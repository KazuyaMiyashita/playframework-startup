package admin

import auth.entity.Token
import io.circe.Json
import io.circe.syntax._

case class AdminLoginResponse(token: Token) {

  def json: Json = Json.obj(
    "token" -> token.value.asJson
  )

}
