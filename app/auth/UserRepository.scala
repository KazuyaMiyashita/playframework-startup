package auth

import auth.entity.{User, Token}
import scala.concurrent.Future

trait UserRepository {

  def createUser(email: String, rawPassword: String, name: String): Future[User]
  def login(email: String, rawPassword: String): Future[Option[Token]]
  def findByToken(token: String): Future[Option[User]]

}
