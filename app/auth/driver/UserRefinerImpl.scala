package auth.driver

import auth.driver.UserRequest
import auth.repository.UserRepository
import cats.data.EitherT
import cats.implicits._
import javax.inject.{Inject, Singleton}
import play.api.mvc.{Request, Result, Results}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRefinerImpl @Inject() (userRepository: UserRepository[Future])(
    override implicit protected val executionContext: ExecutionContext
) extends UserRefiner {
  override protected def refine[A](request: Request[A]): Future[Either[Result, UserRequest[A]]] = {

    def requestFilter(): Future[Either[Result, String]] = Future.successful {
      request.headers.get("Authorization") match {
        case Some(token) => Right(token)
        case None        => Left(Results.BadRequest)
      }
    }

    def checkToken(token: String): Future[Either[Result, UserRequest[A]]] = {
      userRepository.findByToken(token).map {
        case Some(user) => Right(new UserRequest(user, request))
        case None       => Left(Results.BadRequest)
      }
    }

    val et: EitherT[Future, Result, UserRequest[A]] = for {
      token       <- EitherT(requestFilter())
      userRequest <- EitherT(checkToken(token))
    } yield userRequest

    et.value
  }
}
