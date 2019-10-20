package auth.controller

import play.api.data.Form
import play.api.data.Forms._

case class CreateUserForm(
  email: String,
  rawPassword: String,
  name: String
)

object CreateUserForm {

  val form: Form[CreateUserForm] = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText(minLength = 8),
      "name" -> nonEmptyText(minLength = 1, maxLength = 20)
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  )

}
