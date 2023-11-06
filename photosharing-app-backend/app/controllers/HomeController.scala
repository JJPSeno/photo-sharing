package controllers

import models.domains._
import models.domains.Comment
import models.domains.Post
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
import scala.util.Failure
import scala.util.Success
import java.nio.file.Files

@Singleton
class HomeController @Inject()
(val controllerComponents: ControllerComponents, val homeService: HomeService) 
(implicit executionContext: ExecutionContext)
extends BaseController with play.api.i18n.I18nSupport   {

  // val commentForm: Form[Comment] = Form(mapping(
  //   "id"-> default(longNumber, 0L), 
  //   "commentAuthor" -> ignored(""),
  //   "postId" -> default(longNumber, 0L),
  //   "comment"-> nonEmptyText)
  //   (Comment.apply)(Comment.unapply))



  def init() = Action.async{ implicit request: Request[AnyContent] =>
    //fullPostSvc.createSchemas.map( _ => Redirect(routes.HomeController.login()).withNewSession)
    homeService.createSchemas.map(_=>Ok)
  }


  // def getPhoto(id: Long) = Action.async { implicit request =>
  //   val usernameOption = request.session.get("username")
  //   usernameOption.map{ username =>
  //   fullPostSvc.getPhotoFromRepo(id).map{photo =>
  //     Ok(photo.photoBytes).as(s"image/${photo.photoType}")
  //   }
  //   }.getOrElse{
  //       Future.successful(Redirect(routes.HomeController.login()).flashing("loginError" -> "Please login"))
  //   }
  // }

  // def submitComment(id: Long) = Action.async { implicit request =>
  //   commentForm.bindFromRequest().fold(
  //     formWithErrors => {
  //         fullPostSvc.getFullPostv1.map( fps => 
  //           BadRequest(views.html.index(fps, formWithErrors, postForm)) 
  //         )
  //     },
  //     comment => {
  //       val usernameOption = request.session.get("username")
  //       usernameOption.map{username =>
  //         val commentToAdd = comment.copy(commentAuthor = username)
  //         fullPostSvc.addComment(commentToAdd, id).map( _ => Redirect(routes.HomeController.index()).flashing("success" -> ""))
  //       }.getOrElse{
  //         Future.successful(Redirect(routes.HomeController.login()).flashing("loginError" -> "Please login"))
  //       }
  //     }
  //   )
  // }

  // def getAllPhotos() = Action.async { implicit request =>
  //   fullPostSvc.checkPhotoSchema.map(photos => Ok(photos.mkString("\n")))
  // }
  // def getAllComments() = Action.async { implicit request =>
  //   fullPostSvc.checkCommentSchema.map(comments => Ok(comments.mkString("\n"))) 
  // }

}
