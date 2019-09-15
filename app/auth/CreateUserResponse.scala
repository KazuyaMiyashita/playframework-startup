package auth

import auth.entity.User
import io.circe.Json
import io.circe.syntax._

case class CreateUserResponse(user: User) {

  def json: Json = Json.obj(
    "id" -> user.id.asJson,
    "user" -> user.name.asJson
  )

}
