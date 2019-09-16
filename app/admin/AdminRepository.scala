package admin

import auth.entity.{User, Token}
import scala.concurrent.Future

trait AdminRepository {

  def createUser(email: String, rawPassword: String, name: String): Future[User]
  def login(email: String, rawPassword: String): Future[Option[Token]]
  def findByToken(token: String): Future[Option[User]]

}
