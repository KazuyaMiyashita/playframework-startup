package domain.auth.errors

trait AuthorizeErrors

trait LoginError extends AuthorizeErrors

case object AccountNotFoundError extends LoginError
case object WrongPasswordError extends LoginError

trait TokenError extends AuthorizeErrors

case object TokenNotFoundError extends TokenError
case object TokenExpiredError extends TokenError

