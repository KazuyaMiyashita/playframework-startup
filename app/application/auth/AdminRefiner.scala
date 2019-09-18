package application.auth

import javax.inject.Inject
import play.api.mvc.{Request, Result, ActionRefiner}
import scala.concurrent.{Future, ExecutionContext}

abstract class AdminRefiner @Inject()(ec: ExecutionContext)
  extends ActionRefiner[Request, AdminRequest] {

  override def executionContext = ec
  protected def refine[A](request: Request[A]): Future[Either[Result, AdminRequest[A]]]

}
