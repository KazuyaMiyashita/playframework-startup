package auth

import scala.concurrent.Future

trait UserRepository {

  def findByToken(token: String): Future[Option[User]]

}
