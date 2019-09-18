package domain.cafe

import scala.language.higherKinds
import domain.cafe.models.Cafe

trait CafeRepository[F[_]] {

  def findAll(): F[Seq[Cafe]]
  def findById(id: Long): F[Option[Cafe]]
  def add(form: CafeAddForm, userId: Long): F[Option[Cafe]]

}

case class CafeAddForm(
  name: String,
  latitude: BigDecimal,
  longitude: BigDecimal,
  ratingOpt: Option[BigDecimal],
  images: Seq[String]
)
