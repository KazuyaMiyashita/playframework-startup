package application.auth

import javax.inject.Inject
import play.api.mvc.{Request, Result, ActionRefiner}
import play.api.mvc.WrappedRequest
import scala.concurrent.{Future, ExecutionContext}
import domain.auth.entity.UserId

abstract class AuthorizedUserAction @Inject()(ec: ExecutionContext)
  extends ActionRefiner[Request, AuthorizedUserRequest] {

  override def executionContext = ec
  protected def refine[A](request: Request[A]): Future[Either[Result, AuthorizedUserRequest[A]]]

}

class AuthorizedUserRequest[A](val userId: UserId, request: Request[A])
  extends WrappedRequest[A](request)
