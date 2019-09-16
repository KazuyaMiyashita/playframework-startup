package auth

import auth.entity.Token
import io.circe.Json
import io.circe.syntax._

case class LoginResponse(token: Token) {

  def json: Json = Json.obj(
    "token" -> token.value.asJson
  )

}
