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
      userRepository.login(form.email, form.rawPassword).map { tokenOpt =>
        tokenOpt match {
          case None => Left(BadRequest)
          case Some(token) => Right(Ok(TokenResponse(token).json))
        }
      }
    }

    val res: EitherT[Future, Result, Result] = for {
      form <- EitherT(requestFilter(request))
      token <- EitherT(login(form))
    } yield token
    res.value.map(_.merge)
  }

  def create() = Action.async { request =>

    def requestFilter[T](request: Request[T]): Future[Either[Result, CreateUserForm]] = Future.successful {
      bindFromRequest(CreateUserForm.form)(request) match {
        case Right(form) => Right(form)
        case Left(_) => Left(BadRequest)
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
