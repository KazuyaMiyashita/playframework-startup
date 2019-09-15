package auth

import play.api.data.Form
import play.api.data.Forms._

case class LoginForm(
  email: String,
  rawPassword: String
)

object LoginForm {

  val form: Form[LoginForm] = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText(minLength = 8)
    )(LoginForm.apply)(LoginForm.unapply)
  )

}
