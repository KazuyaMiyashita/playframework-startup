package models

case class Image(
  imageType: ImageType,
  url: String
)

trait ImageType
case object MainImage extends ImageType
case object OtherImage extends ImageType
