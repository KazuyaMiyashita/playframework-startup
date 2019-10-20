package auth.controller

import auth.domain.entities.User
import auth.repository.UserRepository
import cats.Monad
import cats.data.EitherT
import cats.implicits._
import javax.inject.{Inject, Singleton}
import play.api.mvc._
import utils.CirceWritable._
import utils.FormUtils.bindFromRequest

import scala.concurrent.Future
import scala.language.higherKinds

@Singleton
class CreateUserController @Inject()(
    cc: ControllerComponents,
    userRepository: UserRepository[Future],
) extends AbstractController(cc) {

  implicit val ec = cc.executionContext

  def create(): Action[AnyContent] = Action.async { request =>
    def requestFilter[T](request: Request[T]): Future[Either[Result, CreateUserForm]] = Future.successful {
      bindFromRequest(CreateUserForm.form)(request) match {
        case Right(form) => Right(form)
        case Left(_)     => Left(BadRequest)
      }
    }

    def create(form: CreateUserForm): Future[Either[Result, User]] = {
      userRepository.createUser(form.email, form.rawPassword, form.name).map(Right(_))
    }

    val res: EitherT[Future, Result, Result] = for {
      form <- EitherT(requestFilter(request))
      user <- EitherT(create(form))
    } yield Ok(CreateUserResponse(user).json)
    res.value.map(_.merge)

  }

}
