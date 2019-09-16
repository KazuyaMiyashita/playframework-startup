package cafe

import cafe.models._
import io.circe.Json
import io.circe.syntax._

case class CafeResponse(
  cafe: Cafe
) {

  def json: Json = {
    val mainPicOpt = cafe.images.headOption.map(_.url)

    Json.obj(
      "name" -> cafe.name.asJson,
      "latitude" -> cafe.coordinate.latitude.asJson,
      "longitude" -> cafe.coordinate.longitude.asJson,
      "star" -> cafe.rating.map(_.value).asJson,
      "main_pic" -> mainPicOpt.fold(Json.Null)(_.asJson)
    )
  }

}

object CafeResponse {

  def fromSeq(cafes: Seq[Cafe]): Json = {
    cafes.map(c => CafeResponse(c).json).asJson
  }

}
