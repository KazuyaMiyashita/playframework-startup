package controllers

import javax.inject._
import play.api.mvc._

import scalikejdbc._
import scalikejdbc.config._

@Singleton
class HealthController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  DBs.setupAll()

  def check() = Action {

    val sel = DB localTx { implicit session =>
      sql"select 1".execute.apply()
    }

    if (sel) Ok
    else InternalServerError

  }

}
