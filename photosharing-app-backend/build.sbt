name := """photosharing-app-backend"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.12"

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-slick" % "5.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
    "com.h2database" % "h2" % "1.4.200",
    "org.postgresql" % "postgresql" % "42.6.0"
)

play.sbt.routes.RoutesKeys.routesImport += "java.util.UUID"
TwirlKeys.templateImports += "play.api.data.Form"
TwirlKeys.templateImports += "views.html.helper.form"
TwirlKeys.templateImports += "models.domains.User"
TwirlKeys.templateImports += "models.repos._"
TwirlKeys.templateImports += "models.services.UserService"
TwirlKeys.templateImports += "models.services.Login"
TwirlKeys.templateImports += "models.services.Register"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
play.sbt.routes.RoutesKeys.routesImport += "play.api.data.Form"
play.sbt.routes.RoutesKeys.routesImport += "views.html.helper.form"
