package admin

import javax.inject.{Singleton, Inject}
import play.api.mvc._
import auth.entity.User
import utils.FormUtils.bindFromRequest
import scala.concurrent.Future
import cats.data.EitherT
import cats.implicits._


import utils.CirceWritable._

@Singleton
class CreateUserController @Inject()(
  cc: ControllerComponents,
  adminRefiner: AdminRefiner,
  adminRepository: AdminRepository,
) extends AbstractController(cc) {

  implicit val ec = cc.executionContext

  def create() = cc.actionBuilder.andThen(adminRefiner).async { request =>

    def requestFilter[T](request: Request[T]): Future[Either[Result, CreateUserForm]] = Future.successful {
      bindFromRequest(CreateUserForm.form)(request) match {
        case Right(form) => Right(form)
        case Left(_) => Left(BadRequest)
      }
    }

    def create(form: CreateUserForm): Future[Either[Result, User]] = {
      adminRepository.createUser(form.email, form.rawPassword, form.name).map(Right(_))
    }

    val res: EitherT[Future, Result, Result] = for {
      form <- EitherT(requestFilter(request))
      user <- EitherT(create(form))
    } yield Ok(CreateUserResponse(user).json)
    res.value.map(_.merge)

  }

}
