package cafe

import javax.inject.{Singleton, Inject}
import play.api.mvc._

import utils.CirceWritable._

@Singleton
class CafeController @Inject()(
  cc: ControllerComponents,
  cafeRepository: CafeRepository,
) extends AbstractController(cc) {

  implicit val ec = cc.executionContext

  def all() = Action.async { request =>
  
    cafeRepository.findAll.map { cafes =>
      Ok(CafeResponse.fromSeq(cafes))
    }

  }

  def findById(id: Long) = Action.async { request =>
    cafeRepository.findById(id).map {
      case Some(cafe) => Ok(CafeResponse(cafe).json)
      case None => NotFound
    }
  }

  def add() = Action.async { request =>
    ???
  }

}
