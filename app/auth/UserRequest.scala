package auth

import play.api.mvc.{Request, WrappedRequest}
import auth.entity.User

class UserRequest[A](val user: User, request: Request[A])
  extends WrappedRequest[A](request)
