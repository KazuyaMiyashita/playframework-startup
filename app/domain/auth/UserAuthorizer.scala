package domain.auth

import scala.language.higherKinds
import domain.auth.entity._
import domain.auth.errors._

trait UserAuthorizer[F[_]] {

  def login(): F[Either[LoginError, UserToken]]
  def logout(token: UserToken): F[Either[TokenError, Unit]]
  def authorize(token: UserToken): F[Either[TokenError, UserId]]

}
