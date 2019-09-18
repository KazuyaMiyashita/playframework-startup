package application.auth

import javax.inject.{Singleton, Inject}
import play.api.mvc._
import utils.FormUtils.bindFromRequest
import scala.concurrent.Future
import cats.data.EitherT
import cats.implicits._

import utils.CirceWritable._

@Singleton
class AdminLoginController @Inject()(
  cc: ControllerComponents,
  adminRepository: AdminRepository,
) extends AbstractController(cc) {

  implicit val ec = cc.executionContext

  def login() = Action.async { request =>

    def requestFilter[T](request: Request[T]): Future[Either[Result, AdminLoginForm]] = Future.successful {
      bindFromRequest(AdminLoginForm.form)(request) match {
        case Right(form) => Right(form)
        case Left(_) => Left(BadRequest)
      }
    }

    def login(form: AdminLoginForm): Future[Either[Result, Result]] = {
      adminRepository.login(form.email, form.rawPassword).map { tokenOpt =>
        tokenOpt match {
          case None => Left(BadRequest)
          case Some(token) => Right(Ok(AdminLoginResponse(token).json))
        }
      }
    }

    val res: EitherT[Future, Result, Result] = for {
      form <- EitherT(requestFilter(request))
      token <- EitherT(login(form))
    } yield token
    res.value.map(_.merge)
  }

}
