package cafe

import play.api.data.Form
import play.api.data.Forms._

case class CafeAddForm(
  email: String,
  rawPassword: String
)

object CafeAddForm {

  val form: Form[CafeAddForm] = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText(minLength = 8)
    )(CafeAddForm.apply)(CafeAddForm.unapply)
  )

}
