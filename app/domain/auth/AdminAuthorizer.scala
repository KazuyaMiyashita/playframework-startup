package domain.auth

import scala.language.higherKinds
import domain.auth.entity._
import domain.auth.errors._

trait AdminAuthorizer[F[_]] {

  def login(): F[Either[LoginError, AdminToken]]
  def logout(token: AdminToken): F[Either[TokenError, Unit]]
  def authorize(token: AdminToken): F[Either[TokenError, Admin]]

}
