package domain.cafe.models

case class Coordinate(
  latitude: BigDecimal,
  longitude: BigDecimal
)

object Coordinate {

  val MinLatitudeValue = BigDecimal(0)
  val MaxLatitudeValue = BigDecimal(90)
  val MinLongitudeValue = BigDecimal(-180)
  val MaxLongitudeValue = BigDecimal(180)

}
