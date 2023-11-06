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
class PostController @Inject()
(val controllerComponents: ControllerComponents, val homeService: HomeService) 
(implicit executionContext: ExecutionContext)
extends BaseController with play.api.i18n.I18nSupport   {

    val postForm: Form[Post] = Form(mapping(
    "message" -> nonEmptyText,
    "commentAuthor" -> ignored(""),
    "userId" -> default(longNumber, 0L),
    "id" -> default(longNumber, 0L),
    )(Post.apply)(Post.unapply))


    def index() = ??? //get all posts
  // def index() = Action.async { implicit request: Request[AnyContent] =>
  //   val usernameOption = request.session.get("username")
  //   usernameOption.map{ username =>
  //     fullPostSvc.getFullPostv1.map{ fps =>
  //       println("fps in index: "+fps)
  //       Ok(views.html.index(fps, commentForm, postForm))}
  //   }.getOrElse{
  //       Future.successful(Redirect(routes.HomeController.login()).flashing("loginError" -> "Please login"))
  //   }
  // }

  //   def submitPost() = Action(parse.multipartFormData).async { implicit request =>
  //   postForm.bindFromRequest().fold(
  //     formWithErrors => {
  //       BadRequest
  //     },
  //     post => {
  //         val userIdOption = request.session.get("userId")
  //         userIdOption.map{ userId => 
  //           val usernameOption = request.session.get("username")
  //           usernameOption.map{ username =>
  //             request.body
  //             .file("picture")
  //             .map { picture =>
  //               val fileType = picture.filename.split("\\.+").toList.last
  //               val fileBytes = Files.readAllBytes(Paths.get(picture.ref.getAbsolutePath()))
  //               val postToAdd: Post = Post(message= post.message, postAuthor = username, userId=userId.toLong, id=0)
  //               println("Post in submitpost: "+postToAdd.toString())
  //               val photoToAdd: Photo = Photo(id = 0L, postId = post.id, photoType=fileType, photoBytes=fileBytes)
  //               println("Photo in submitpost: "+photoToAdd.toString())
  //               fullPostSvc.addFullPost(postToAdd, photoToAdd).flatMap( _ => 
  //                 fullPostSvc.getFullPostv1.map( _ => 
  //                   Redirect(routes.HomeController.index()).flashing("success" -> "Photo Uploaded!")
  //                 )  
  //               )
  //             }.getOrElse {
  //               Future.successful(Redirect(routes.HomeController.index()).flashing("error" -> "Missing file."))
  //             }
  //           }.getOrElse{
  //             Future.successful(Redirect(routes.HomeController.login()).flashing("loginError" -> "Please login"))
  //           }
  //         }.getOrElse{
  //           Future.successful(Redirect(routes.HomeController.login()).flashing("loginError" -> "Please login"))
  //         }
  //     }
  //   )
  // }
      def submitPost() = Action(parse.multipartFormData).async { implicit request =>
    postForm.bindFromRequest().fold(
      formWithErrors => {
        Future.successful(BadRequest)
      },
      post => {
        homeService.addPost( post.message, post.postAuthor, post.userId ).map(_ => Ok)
      })
  }

    def getAllPosts() = Action.async { implicit request =>
    homeService.checkPostSchema.map(posts => Ok(posts.mkString("\n")))
  }

}
