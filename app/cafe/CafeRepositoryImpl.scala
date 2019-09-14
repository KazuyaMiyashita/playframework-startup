package cafe

import cafe.models.{Cafe, Coodinate, Image, Rating}
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import scalikejdbc._

import scala.concurrent.ExecutionContext

@Singleton
class CafeRepositoryImpl @Inject()(
                                    implicit ec: ExecutionContext
                                  ) extends CafeRepository {
  implicit val session = AutoSession
  def mkCafeEntity(rs: WrappedResultSet) = Cafe(
    id = rs.get("id"),
    name = rs.get("name"),
    coodinate = Coodinate(rs.get("lattiude"), rs.get("longtitude")),
    rating = Rating(rs.bigDecimalOpt("averaged_value").map(BigDecimal(_))),
    images  = rs.stringOpt("urls").map(_.split(",").map(Image).toSeq).getOrElse(Seq[Image]())
  )

  override def findAll(): Future[Seq[Cafe]] =
    Future {
      sql"""
        select c.cafe_id as id, c.lattiude as lattiude, c.longtitude as longtitude, c.name as name, group_concat(distinct i.url) as urls, avg(r.value) as averaged_value
          from cafes as c
            left join images as i
              using (cafe_id)
            left join ratings as r
              using (cafe_id)
            group by c.cafe_id;
      """
        .map(rs => mkCafeEntity(rs))
        .list
        .apply()
    }


  override def findById(id: Long): Future[Option[Cafe]] = findAll.map(_.find(_.id == id))

}
