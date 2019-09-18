package gateawy.auth

import javax.inject.{Singleton, Inject}
import play.api.mvc.{Request, Result}
import scala.concurrent.{Future, ExecutionContext}
import play.api.mvc.Results
import cats.data.EitherT
import cats.implicits._

@Singleton
class UserRefinerImpl @Inject()(
  implicit ec: ExecutionContext,
  userRepository: UserRepository
) extends UserRefiner(ec) {

  protected def refine[A](request: Request[A]):
    Future[Either[Result, UserRequest[A]]] = {

    def requestFilter(): Future[Either[Result, String]] = Future.successful {
      request.headers.get("Authorization") match {
        case Some(token) => Right(token)
        case None => Left(Results.BadRequest)
      }
    }

    def checkToken(token: String): Future[Either[Result, UserRequest[A]]] = {
      userRepository.findByToken(token).map {
        case Some(user) => Right(new UserRequest(user, request))
        case None => Left(Results.BadRequest)
      }
    }

    val et: EitherT[Future, Result, UserRequest[A]] = for {
      token <- EitherT(requestFilter())
      userRequest <- EitherT(checkToken(token))
    } yield userRequest
    et.value
  }

}
