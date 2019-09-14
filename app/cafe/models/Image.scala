package cafe.models

case class Image(
  url: String
)

trait ImageType
object ImageTypes {
  case object MainImage extends ImageType
  case object OtherImage extends ImageType
}
