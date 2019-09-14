package auth

import javax.inject.{Singleton, Inject}
import play.api.mvc._

@Singleton
class AuthController @Inject()(
  cc: ControllerComponents,
  cafeRepository: CafeRepository,
) extends AbstractController(cc) {

  implicit val ec = cc.executionContext

  def login() = Action.async { request =>
  
    cafeRepository.findAll.map { cafes =>
      Ok(CafeResponse.fromSeq(cafes))
    }

  }

}
