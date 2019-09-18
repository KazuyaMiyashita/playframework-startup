package application.auth

import domain.auth.entity.UserToken
import io.circe.Json
import io.circe.syntax._

case class LoginResponse(token: UserToken) {

  def json: Json = Json.obj(
    "token" -> token.value.asJson
  )

}
