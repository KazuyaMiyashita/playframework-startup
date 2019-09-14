package models

case class Rating(
  value: BigDecimal
)

object Rating {

  val MinValue = BigDecimal(1)
  val MaxValue = BigDecimal(5)

}
