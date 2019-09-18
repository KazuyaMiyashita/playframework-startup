package domain.admin

import domain.auth.entity.{AdminToken, UserId}
import scala.language.higherKinds

trait AdminOperations[F[_]] {

  def createUser(adminToken: AdminToken, createUserForm: CreateUserForm): F[Either[CreateUserError, UserId]]

}

case class CreateUserForm(email: String, rawPassword: String, name: String)

trait CreateUserError
