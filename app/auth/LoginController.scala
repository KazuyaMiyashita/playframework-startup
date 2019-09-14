package auth

import javax.inject.{Singleton, Inject}
import play.api.mvc._
import utils.FormUtils.bindFromRequest
import scala.concurrent.Future
import cats.data.EitherT
import cats.implicits._


import utils.CirceWritable._

@Singleton
class AuthController @Inject()(
  cc: ControllerComponents,
  userRepository: UserRepository,
) extends AbstractController(cc) {

  implicit val ec = cc.executionContext

  def login() = Action.async { request =>

    def requestFilter[T](request: Request[T]): Future[Either[Result, LoginForm]] = Future.successful {
      bindFromRequest(LoginForm.form)(request) match {
        case Right(form) => Right(form)
        case Left(_) => Left(BadRequest)
      }
    }

    def login(form: LoginForm): Future[Either[Result, Result]] = {
      val et: EitherT[Future, Result, Result] = 
        EitherT.fromOptionF(
          userRepository.login(form.email, form.rawPassword),
          BadRequest
        ).flatMap { token =>
          Right(Ok(TokenResponse(token).json))
        }
      // val et: EitherT[Future, Result, Result] =
      //   EitherT.fromOption(userRepository.login(form.email, form.rawPassword)).flatMap({ _ match {
      //   case Some(token) => Right(Ok(TokenResponse(token).json))
      //   case None => Left(BadRequest)
      // }})
      et.value
    }

    val ec: EitherT[Future, Result, Result] = for {
      form <- EitherT(requestFilter(request))
      token <- EitherT(login(form))
    } yield token
    ec.value
  }

}
