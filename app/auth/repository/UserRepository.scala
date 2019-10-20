package auth.repository

import auth.domain.entities.{Token, User}

import scala.language.higherKinds

trait UserRepository[F[_]] {
  def createUser(email: String, rawPassword: String, name: String): F[User]
  def login(email: String, rawPassword: String): F[Option[Token]]
  def findByToken(token: String): F[Option[User]]
}
