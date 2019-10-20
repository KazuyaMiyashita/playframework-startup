package auth.driver

import auth.controller.UserRequest
import play.api.mvc.{ActionRefiner, Request, Result}

import scala.concurrent.{ExecutionContext, Future}

trait UserRefiner extends ActionRefiner[Request, UserRequest] {
  override protected def executionContext: ExecutionContext
  override protected def refine[A](request: Request[A]): Future[Either[Result, UserRequest[A]]]
}
