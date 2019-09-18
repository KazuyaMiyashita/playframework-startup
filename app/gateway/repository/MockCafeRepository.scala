package gateway.repository

import javax.inject.{Singleton, Inject}
import scala.concurrent.Future
import models._
import scala.concurrent.ExecutionContext

@Singleton
class MockCafeRepository @Inject()(
  implicit ec: ExecutionContext
) extends CafeRepository {

  override def findAll(): Future[Seq[Cafe]] = Future.successful {
    Cafe(
      id = 1,
      name = "BUoY Cafe",
      coordinate = Coordinate(BigDecimal("35.7435032"), BigDecimal("139.8000034")),
      rating = Some(Rating(BigDecimal("4.5"))),
      images = Nil
    ) ::
    Cafe(
      id = 2,
      name = "コメダ珈琲店 田端駅前店",
      coordinate = Coordinate(BigDecimal("35.7380439"), BigDecimal("139.7574194")),
      rating = Some(Rating(BigDecimal("3.0"))),
      images = Nil
    ) :: Nil
  }

  override def findById(id: Long): Future[Option[Cafe]] = findAll.map(_.find(_.id == id))

  override def add(form: CafeAddForm, userId: Long): Future[Option[Cafe]] = ???
  
}
