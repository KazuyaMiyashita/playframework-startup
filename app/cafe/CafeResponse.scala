package cafe

import cafe.models._
import io.circe.Json
import io.circe.syntax._

case class CafeResponse(
  cafe: Cafe
) {

  def json: Json = {
    import _root_.cafe.models.ImageTypes.MainImage
    val mainPicOpt = cafe.images.find(_.imageType == MainImage).map(_.url)

    Json.obj(
      "name" -> cafe.name.asJson,
      "latitude" -> cafe.coodinate.latitude.asJson,
      "longitude" -> cafe.coodinate.longitude.asJson,
      "star" -> cafe.rating.value.asJson,
      "main_pic" -> mainPicOpt.fold(Json.Null)(_.asJson)
    )
  }

}

object CafeResponse {

  def fromSeq(cafes: Seq[Cafe]): Json = {
    cafes.map(c => CafeResponse(c).json).asJson
  }

}
