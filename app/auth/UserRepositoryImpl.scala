package auth

import javax.inject.{Singleton, Inject}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scalikejdbc._
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import scala.concurrent.duration._

@Singleton
class UserRepositoryImpl @Inject()(
  implicit ec: ExecutionContext
) extends UserRepository {

  implicit val session = AutoSession
  val tokenExpirationDuration = 1.day

  private val bcrypt = new BCryptPasswordEncoder()
  private def createHash(password: String): String = bcrypt.encode(password)
  private def authenticate(rawPassword: String, hashedPassword: String): Boolean =
    bcrypt.matches(rawPassword, hashedPassword)

  def createUser(email: String, rawPassword: String, name: String): Future[User] = Future {
    val hashedPassword = createHash(rawPassword)
    val authId: Long = sql"insert into auths (email, password) values (${email}, ${hashedPassword})"
      .updateAndReturnGeneratedKey.apply()
    val userId: Long = sql"insert into users (auth_id, name) values (${authId}, ${name})"
      .updateAndReturnGeneratedKey.apply()
    User(userId, name)
  }

  def login(email: String, rawPassword: String): Future[Option[Token]] = {

    case class Auth(authId: Long, hashedPassword: String)
    def findPasswordFromEmail(email: String): Option[Auth] = {
      def toHashedPassword(rs: WrappedResultSet) = Auth(
        authId = rs.get("auth_id"),
        hashedPassword = rs.get("hashed_password"),
      )
      sql"select auth_id, hashed_password from auths where email = ${email}"
        .map(rs => toHashedPassword(rs))
        .single.apply()
    }

    def saveToken(auth: Auth): Token = {
      def createRandomToken(): Token = {
        import util.Random
        val rnd = new Random
        rnd.setSeed(java.util.Calendar.getInstance.getTimeInMillis)

        val ts = (('A' to 'Z').toList :::
        ('a' to 'z').toList :::
        ('0' to '9').toList :::
        List('-', '.', '_', '~', '+', '/'))
        .toArray
  
        val tsLen = ts.length
        val length = 64
    
        Token("Bearer " + List.fill(length)(ts(rnd.nextInt(tsLen))).mkString)
      }
      val token = createRandomToken()
      sql"insert into tokens (token, auth_id) values (${token}, ${auth.authId})"
        .update.apply()

      token
    }

    Future {
      for {
        auth <- findPasswordFromEmail(email) if authenticate(rawPassword, auth.hashedPassword)
        token = saveToken(auth)
      } yield token
    }
  }

  def findByToken(token: String): Future[Option[User]] = {
    def mkUserEntity(rs: WrappedResultSet) = User(
      id = rs.get("user_id"),
      name = rs.get("name")
    )

    Future {
      sql"""
        select u.user_id as user_id, u.name as name
          from tokens as t
            inner join auths using auth_id
            inner join users using auth_id
          where
            token = ${token}
            and created_at >= current_timestamp - interval ${tokenExpirationDuration.toMinutes} minute;
      """
        .map(rs => mkUserEntity(rs))
        .single.apply()
    }

  }

  
}
