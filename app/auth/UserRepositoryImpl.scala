package auth

import javax.inject.{Singleton, Inject}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scalikejdbc._
import scala.concurrent.duration._

@Singleton
class UserRepositoryImpl @Inject()(
  implicit ec: ExecutionContext
) extends UserRepository {

  implicit val session = AutoSession
  val tokenExpirationDuration = 1.day

  def findByToken(token: String): Future[Option[User]] = {
    def mkUserEntity(rs: WrappedResultSet) = User(
      id = rs.get("id"),
      name = rs.get("name")
    )

    Future {
      sql"""
        select u.id as id, u.name as name
          from tokens as t
            inner join auths as a
              on t.auth_id = a.id
            inner join users as u
              on a.id = u.auth_id
          where
            token = ${token}
            and created_at >= current_timestamp - interval ${tokenExpirationDuration.toMinutes} minute;
      """
        .map(rs => mkUserEntity(rs))
        .single.apply()
    }

  }

  
}
