package models.repos

import models.domains._
import javax.inject._
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostRepo @Inject() 
(protected val dbConfigProvider: DatabaseConfigProvider, val userRepo: UserRepo)
(implicit executionContext: ExecutionContext) 
extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  protected class PostTable(tag: Tag) extends Table[Post](tag, "POSTS"){
    def message = column[String]("MESSAGE", O.Length(255))
    def postAuthor = column[String]("POST_AUTHOR", O.Length(50))
    def userId = column[Long]("USER_ID")
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

    def user = foreignKey("FK_USER", userId, userRepo.UserTable)(_.id, onDelete = ForeignKeyAction.Cascade)

    def * = (message, postAuthor, userId, id).mapTo[Post]
  }

  lazy val PostTable = TableQuery[PostTable]
  def createPostSchema = PostTable.schema.createIfNotExists

  def all() = db.run(PostTable.result)
  def getPost(targetPostId: Long) = db.run(PostTable.filter(_.id === targetPostId).result)
  def getPostByUser(targetUserId: Long) = db.run(PostTable.filter(_.userId === targetUserId).result)
  def insert(newPost: Post) = {
    db.run((PostTable returning PostTable.map(_.id)) += newPost)
  }
  
}