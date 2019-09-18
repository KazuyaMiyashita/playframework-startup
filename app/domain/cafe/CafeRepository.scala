package domain.cafe

import scala.language.higherKinds
import domain.auth.entity.UserId
import domain.cafe.models.Cafe

trait CafeRepository[F[_]] {

  def findAll(): F[Seq[Cafe]]
  def findById(id: Long): F[Option[Cafe]]
  def add(form: CafeAddForm, userId: UserId): F[Option[Cafe]]

}

case class CafeAddForm(
  name: String,
  latitude: BigDecimal,
  longitude: BigDecimal,
  ratingOpt: Option[BigDecimal],
  images: Seq[String]
)
