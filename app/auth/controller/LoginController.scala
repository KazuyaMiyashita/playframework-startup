package auth.controller

import auth.repository.UserRepository
import cats.data.EitherT
import cats.implicits._
import javax.inject.{Inject, Singleton}
import play.api.mvc._
import utils.CirceWritable._
import utils.FormUtils.bindFromRequest

import scala.concurrent.Future

@Singleton
class LoginController @Inject()(
    cc: ControllerComponents,
    userRepository: UserRepository[Future],
) extends AbstractController(cc) {

  implicit val ec = cc.executionContext

  def login(): Action[AnyContent] = Action.async { request =>
    def requestFilter[T](request: Request[T]): Future[Either[Result, LoginForm]] = Future.successful {
     bindFromRequest(LoginForm.form)(request) match {
       case Right(form) => Right(form)
       case Left(_)     => Left(BadRequest)
     }
   }

    def login(form: LoginForm): Future[Either[Result, Result]] = {
      userRepository.login(form.email, form.rawPassword).map { tokenOpt =>
        tokenOpt match {
          case None        => Left(BadRequest)
          case Some(token) => Right(Ok(LoginResponse(token).json))
        }
      }
    }

    val res: EitherT[Future, Result, Result] = for {
      form  <- EitherT(requestFilter(request))
      token <- EitherT(login(form))
    } yield token
    res.value.map(_.merge)
  }
}
