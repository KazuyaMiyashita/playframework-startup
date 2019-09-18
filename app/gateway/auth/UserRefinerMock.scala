package gateawy.auth

import javax.inject.{Singleton, Inject}
import play.api.mvc.{Request, Result}
import scala.concurrent.{Future, ExecutionContext}

import auth.entity.User

@Singleton
class UserRefinerMock @Inject()(implicit ec: ExecutionContext) extends UserRefiner(ec) {

  override protected def refine[A](request: Request[A]): Future[Either[Result, UserRequest[A]]] = Future.successful {

    val mockUser = User(1, "asai")
    val mockUserRequest = new UserRequest(mockUser, request)
    Right(mockUserRequest)
    
  }

}
