package cafe

import models.Cafe
import scala.concurrent.Future

trait CafeRepository {

  def findAll(): Future[Seq[Cafe]]
  def findById(id: Long): Future[Option[Cafe]]
  def add(form: CafeAddForm): Future[Option[Cafe]]

}
