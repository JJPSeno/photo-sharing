package controllers

import models.domains._
import models.repos._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.MessagesApi
import java.nio.file.Paths
import play.api.data._
import play.api.data.Forms._
import models.services.HomeService
import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.io.File
import play.api.i18n.Lang
import play.api.i18n.Langs
import play.api.http.HttpEntity
import models.services.Login
import models.services.Register
import models.domains.User
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.util.Failure
import scala.util.Success
import java.nio.file.Files

@Singleton
class UserController @Inject()
(val controllerComponents: ControllerComponents, val homeService: HomeService) 
(implicit executionContext: ExecutionContext)
extends BaseController with play.api.i18n.I18nSupport   {

  case class UserToJson(id: Long, username: String)

  implicit val userWrites = new Writes[UserToJson] {
    def writes(user: UserToJson) = Json.obj(
      "id"      -> user.id,
      "username"  -> user.username,
    )
  }

  implicit val userReads: Reads[User] = (
  (JsPath \ "id").read[Long] and
    (JsPath \ "username").read[String] and
    (JsPath \ "password").read[String]
  )(User.apply _)

  val loginForm: Form[Login] = Form(mapping(
    "username"-> nonEmptyText, 
    "password" -> nonEmptyText)
    (Login.apply)(Login.unapply))

  val registerForm: Form[Register] = Form(mapping(
    "username"-> nonEmptyText, 
    "password" -> nonEmptyText)
    (Register.apply)(Register.unapply))

  def init() = Action.async{ implicit request: Request[AnyContent] =>
    homeService.createSchemas.map(_=>Ok)
  }


  def submitLogin() = Action.async { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => {
        Future.successful(BadRequest)
      },
      login => {
        homeService.validateUser(login.username, login.password).map { 
            case Some(user) =>
              if (login.password == user.password) {
                Ok(Json.toJson(UserToJson(user.id, user.username)))
              } else {
                BadRequest
              }
              case None => Unauthorized
          }
      }
    )
  }

  // def submitLogin() = Action.async { implicit request =>
  //   loginForm.bindFromRequest().fold(
  //     formWithErrors => {
  //       Future.successful(BadRequest)
  //     },
  //     login => {
  //       homeService.validateUser(login.username, login.password).map{ userOption =>
  //         userOption.map{ user =>
  //             if (login.password == user.password) {
  //               Ok(Json.toJson(UserToJson(user.id, user.username)))
  //             } else {
  //               Future.successful(BadRequest)
  //             }
  //         }
  //       }
  //     }
  //   )
  // }

  def submitRegistration() = Action.async { implicit request =>
    registerForm.bindFromRequest().fold(
      formWithErrors => {
        Future.successful(BadRequest)
      },
      register => {
        homeService.registerUser(register.username, register.password).map(_ => Ok)
        })
      }

  def getAllUsers() = Action.async { implicit request =>
    homeService.checkUserSchema.map(comments => Ok(comments.mkString("\n"))) 
  }
}
