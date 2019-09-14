package cafe.models

case class Image(
  imageType: ImageType,
  url: String
)

trait ImageType
object ImageTypes {
  case object MainImage extends ImageType
  case object OtherImage extends ImageType
}
