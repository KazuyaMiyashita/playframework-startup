import sbt._

object Dependencies {
  lazy val scalaTestPlusPlay = "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3"
  lazy val cats = "org.typelevel" %% "cats-core" % "2.0.0"
  lazy val circe = Seq(
    "io.circe" %% "circe-core" % "0.12.1",
    "io.circe" %% "circe-generic" % "0.12.1",
    "io.circe" %% "circe-parser" % "0.12.1"
  )
  lazy val mysql = "mysql" % "mysql-connector-java" % "8.0.17"
  lazy val scalikejdbc = Seq(
    "org.scalikejdbc" %% "scalikejdbc" % "3.3.5",
    "org.scalikejdbc" %% "scalikejdbc-config"  % "3.3.5",
  )
}
