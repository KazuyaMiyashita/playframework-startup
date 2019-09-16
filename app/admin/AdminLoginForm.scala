package admin

import play.api.data.Form
import play.api.data.Forms._

case class AdminLoginForm(
  email: String,
  rawPassword: String
)

object AdminLoginForm {

  val form: Form[AdminLoginForm] = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText(minLength = 8)
    )(AdminLoginForm.apply)(AdminLoginForm.unapply)
  )

}
