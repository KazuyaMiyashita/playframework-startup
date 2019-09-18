package application.auth

import javax.inject.Inject
import play.api.mvc.{Request, Result, ActionRefiner}
import play.api.mvc.WrappedRequest
import scala.concurrent.{Future, ExecutionContext}
import domain.auth.entity.AdminId

abstract class AuthorizedAdminAction @Inject()(ec: ExecutionContext)
  extends ActionRefiner[Request, AuthorizedAdminRequest] {

  override def executionContext = ec
  protected def refine[A](request: Request[A]): Future[Either[Result, AuthorizedAdminRequest[A]]]

}

class AuthorizedAdminRequest[A](val user: AdminId, request: Request[A])
  extends WrappedRequest[A](request)
