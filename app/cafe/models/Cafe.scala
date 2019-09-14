package cafe.models

case class Cafe(
  id: Long,
  name: String,
  coodinate: Coodinate,
  rating: Rating,
  images: Seq[Image]
)
