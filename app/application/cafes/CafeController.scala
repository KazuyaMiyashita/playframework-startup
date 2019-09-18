package application.cafe

import javax.inject.{Singleton, Inject}
import play.api.mvc._
import domain.auth.entity.UserId
import domain.cafe.CafeRepository
import domain.cafe.CafeAddForm
import domain.cafe.models.Cafe
import application.auth.AuthorizedUserAction
import application.utils.FormUtils.bindFromRequest
import scala.concurrent.Future
import cats.data.EitherT
import cats.implicits._

import application.utils.CirceWritable._

@Singleton
class CafeController @Inject()(
  cc: ControllerComponents,
  authorizedUserAction: AuthorizedUserAction,
  cafeRepository: CafeRepository[Future],
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

  def add() = Action.andThen(authorizedUserAction).async { request =>

    val userId = request.userId

    def requestFilter[T](request: Request[T]): Future[Either[Result, CafeAddForm]] = Future.successful {
      bindFromRequest(CafeAddRequest.handler)(request) match {
        case Right(form) => Right(form)
        case Left(_) => Left(BadRequest)
      }
    }

    def insertCafe(form: CafeAddForm, userId: UserId): Future[Either[Result, Cafe]] = {
      cafeRepository.add(form, userId).map { cafeOpt =>
        cafeOpt match {
          case None => Left(InternalServerError)
          case Some(cafe) => Right(cafe)
        }
      }
    }

    val res: EitherT[Future, Result, Result] = for {
      form <- EitherT(requestFilter(request))
      cafe <- EitherT(insertCafe(form, userId))
    } yield Ok(CafeResponse(cafe).json)
    res.value.map(_.merge)
  }

}
