package cafe.models

case class Rating(
  value: Option[BigDecimal]
)

object Rating {

  val MinValue = BigDecimal(1)
  val MaxValue = BigDecimal(5)

}
