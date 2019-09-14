package models

case class CafeId(value: Long)
case class Cafe(
  name: String,
  coodinate: Coodinate,
  rating: Rating,
  images: Seq[Image]
)
