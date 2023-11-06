package models.services

import models.domains._
import models.repos._
import javax.inject._
import play.api.data._
import play.api.data.Forms._
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import scala.collection.mutable.ListBuffer

final case class Login(
  username: String,
  password: String
)

final case class Register(
  username: String,
  password: String
)

@Singleton
class HomeService @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider, 
  val userRepo: UserRepo, val postRepo: PostRepo
)
(implicit executionContext: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  def createSchemas ={ 

    db.run(
      userRepo.createUserSchema andThen
      postRepo.createPostSchema 
    )

  }

  def checkUserSchema = userRepo.all()
  def checkPostSchema = postRepo.all()

  def registerUser(regUsername: String, regPassword: String) = {
    val newUser = User(username = regUsername, password = regPassword)
    userRepo.insert(newUser)
  }

  def validateUser(loginUsername: String, loginPassword: String) = {
    userRepo.getUser(loginUsername)
  }

  def addPost(formMessage: String, formPostAuthor: String, formUserId: Long) = {
    val newPost = Post(message = formMessage, postAuthor = formPostAuthor, userId = formUserId)
    postRepo.insert(newPost)
  }

  def getAllPosts() = {
    postRepo.all()
  }
  
}
