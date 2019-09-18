package application.cafe

import domain.cafe.CafeAddForm
import domain.cafe.models.Coordinate
import domain.cafe.models.Rating
import play.api.data.Form
import play.api.data.Forms._

object CafeAddForm {

  val form: Form[CafeAddForm] = Form(
    mapping(
      "name" -> nonEmptyText(maxLength = 32),
      "latitude" -> bigDecimal
        .verifying("latitude must be greater than 0", _ >= Coordinate.MinLatitudeValue)
        .verifying("latitude must be less than 90", _ <= Coordinate.MaxLatitudeValue),
      "longitude" -> bigDecimal
        .verifying("longitude must be greater than 0", _ >= Coordinate.MinLongitudeValue)
        .verifying("longitude must be less than 90", _ <= Coordinate.MaxLongitudeValue),
      "star" -> optional(bigDecimal)
        .verifying("star must be greater than 0", _.fold(true)(_ >= Rating.MinValue))
        .verifying("star must be less than 90", _.fold(true)(_ <= Rating.MaxValue)),
      "images" -> seq(nonEmptyText)
    )(CafeAddForm.apply)(CafeAddForm.unapply)
  )

}
