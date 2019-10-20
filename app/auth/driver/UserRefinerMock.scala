package auth.driver

import auth.driver.UserRequest
import auth.domain.entities.User
import javax.inject.{Inject, Singleton}
import play.api.mvc.{Request, Result}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRefinerMock @Inject() (implicit override protected val executionContext: ExecutionContext)
    extends UserRefiner {

  override protected def refine[A](request: Request[A]): Future[Either[Result, UserRequest[A]]] = Future.successful {

    val mockUser        = User(1, "asai")
    val mockUserRequest = new UserRequest(mockUser, request)
    Right(mockUserRequest)

  }

}
