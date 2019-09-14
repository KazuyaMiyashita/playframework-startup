package auth

import io.circe.Json
import io.circe.syntax._

case class TokenResponse(token: Token) {

  def json: Json = Json.obj(
    "token" -> token.value.asJson
  )

}
