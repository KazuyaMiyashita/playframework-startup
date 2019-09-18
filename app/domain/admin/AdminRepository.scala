package domain.admin

import domain.auth.entity.{User, Token}
import scala.language.higherKinds

trait AdminRepository[F[_]] {

  def createUser(email: String, rawPassword: String, name: String): F[User]

}
