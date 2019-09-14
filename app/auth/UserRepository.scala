package auth

import scala.concurrent.Future

trait UserRepository {

  def login(email: String, rawPassword: String): Future[Option[Token]]
  def findByToken(token: String): Future[Option[User]]

}
