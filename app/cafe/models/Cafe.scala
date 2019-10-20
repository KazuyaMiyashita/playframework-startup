package cafe.models

case class Cafe(
    id: Long,
    name: String,
    coordinate: Coordinate,
    rating: Option[Rating],
    images: Seq[Image]
)
