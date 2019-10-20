package cafe

import cafe.models.{Cafe, Coordinate, Image, Rating}
import javax.inject.{Inject, Singleton}

import scala.concurrent.Future
import scalikejdbc._

import scala.concurrent.ExecutionContext

case class CafeRecord(
    cafe_id: Long,
    name: String,
    latitude: BigDecimal,
    longitude: BigDecimal,
    ratings: Seq[RatingRecord] = Nil,
    images: Seq[ImageRecord] = Nil
)

case class ImageRecord(
    cafe_id: Long,
    url: String
)

case class RatingRecord(
    cafe_id: Long,
    user_id: Long,
    value: BigDecimal
)

object CafeRecord extends SQLSyntaxSupport[CafeRecord] {
  override val tableName = "cafes"
  def apply(c: SyntaxProvider[CafeRecord])(rs: WrappedResultSet): CafeRecord =
    apply(c.resultName)(rs)
  def apply(c: ResultName[CafeRecord])(rs: WrappedResultSet): CafeRecord =
    new CafeRecord(
      rs.get(c.cafe_id),
      rs.get(c.name),
      rs.get(c.latitude),
      rs.get(c.longitude)
    )
}

object ImageRecord extends SQLSyntaxSupport[ImageRecord] {
  override val tableName = "images"
  def apply(m: SyntaxProvider[ImageRecord])(rs: WrappedResultSet): ImageRecord =
    apply(m.resultName)(rs)
  def apply(m: ResultName[ImageRecord])(rs: WrappedResultSet): ImageRecord =
    ImageRecord(rs.get(m.cafe_id), rs.get(m.url))

  def opt(
      m: SyntaxProvider[ImageRecord]
  )(rs: WrappedResultSet): Option[ImageRecord] =
    rs.stringOpt(m.resultName.url).map(_ => ImageRecord(m)(rs))
}

object RatingRecord extends SQLSyntaxSupport[RatingRecord] {
  override val tableName = "ratings"
  def apply(r: SyntaxProvider[RatingRecord])(
      rs: WrappedResultSet
  ): RatingRecord = apply(r.resultName)(rs)
  def apply(r: ResultName[RatingRecord])(rs: WrappedResultSet): RatingRecord =
    RatingRecord(rs.get(r.cafe_id), rs.get(r.user_id), rs.get(r.value))

  def opt(
      m: SyntaxProvider[RatingRecord]
  )(rs: WrappedResultSet): Option[RatingRecord] =
    rs.stringOpt(m.resultName.value).map(_ => RatingRecord(m)(rs))
}

@Singleton
class CafeRepositoryImpl @Inject() (implicit ec: ExecutionContext) extends CafeRepository {
  implicit val session = AutoSession

  val (c, i, r) = (CafeRecord.syntax, ImageRecord.syntax, RatingRecord.syntax)

  val baseSqlBuildeer: SelectSQLBuilder[CafeRecord] = select
    .from(CafeRecord as c)
    .leftJoin(RatingRecord as r)
    .on(c.cafe_id, r.cafe_id)
    .leftJoin(ImageRecord as i)
    .on(c.cafe_id, i.cafe_id)

  def buildCafeSql(sql: SQLBuilder[CafeRecord]) =
    withSQL {
      sql
    }.one(CafeRecord(c))
      .toManies(ImageRecord.opt(i), RatingRecord.opt(r))
      .map { (cafe, images, ratings) =>
        cafe.copy(images = images.toSeq, ratings = ratings.toSeq)
      }

  def mkCafeEntity(cafeRecord: CafeRecord): Cafe = {
    val averageRating = cafeRecord.ratings
      .map(_.value)
      .reduceOption((acc, value) => acc + (value / cafeRecord.ratings.length))
    val images = cafeRecord.images.map(ir => Image(ir.url))
    Cafe(
      cafeRecord.cafe_id,
      cafeRecord.name,
      Coordinate(cafeRecord.latitude, cafeRecord.longitude),
      averageRating.map(Rating(_)),
      images
    )
  }

  override def findAll(): Future[Seq[Cafe]] =
    Future {
      buildCafeSql(baseSqlBuildeer).list
        .apply()
        .map(mkCafeEntity)
    }

  override def findById(id: Long): Future[Option[Cafe]] =
    Future {
      buildCafeSql(baseSqlBuildeer.where.eq(c.cafe_id, id)).single
        .apply()
        .map(mkCafeEntity)
    }

  override def add(form: CafeAddForm, userId: Long): Future[Option[Cafe]] = {
    def insertIntoCafes(form: CafeAddForm)(implicit session: AutoSession) = {
      withSQL {
        insert
          .into(CafeRecord)
          .namedValues(
            c.name      -> form.name,
            c.latitude  -> form.latitude,
            c.longitude -> form.longitude
          )
      }.updateAndReturnGeneratedKey.apply()
    }
    def insertIntoRatings(cafeId: Long, userId: Long, value: BigDecimal)(
        implicit session: AutoSession
    ) = {
      withSQL {
        insert
          .into(RatingRecord)
          .namedValues(
            r.cafe_id -> cafeId,
            r.user_id -> userId,
            r.value   -> value
          )
      }.update.apply()
    }
    def insertIntoImages(cafeId: Long, imageUrls: Seq[String])(
        implicit session: AutoSession
    ) = {
      val batchParams: Seq[Seq[Any]] = imageUrls.map(u => cafeId :: u :: Nil)
      withSQL {
        insert
          .into(ImageRecord)
          .namedValues(i.cafe_id -> sqls.?, i.url -> sqls.?)
      }.batch(batchParams: _*).apply()
    }

    def insertCafe() = Future {
      val cafeId = insertIntoCafes(form)
      form.ratingOpt.foreach { rating =>
        insertIntoRatings(cafeId, userId, rating)
      }
      if (form.images.nonEmpty) {
        insertIntoImages(cafeId, form.images)
      }
      cafeId
    }

    for {
      cafeId  <- insertCafe()
      cafeOpt <- findById(cafeId)
    } yield cafeOpt

  }

}
