package admin

import play.api.mvc.{Request, WrappedRequest}
import admin.entity.Admin

class AdminRequest[A](val user: Admin, request: Request[A])
  extends WrappedRequest[A](request)
