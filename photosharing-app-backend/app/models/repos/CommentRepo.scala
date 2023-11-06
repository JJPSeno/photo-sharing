package models.repos

import models.domains._
import javax.inject._
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CommentRepo @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider, val postRepo: PostRepo, val userRepo: UserRepo)
(implicit executionContext: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  protected class CommentTable(tag: Tag) extends Table[Comment](tag, "COMMENTS"){
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def commentAuthor = column[String]("COMMENT_AUTHOR")
    def postId = column[Long]("POST_ID")
    def comment = column[String]("COMMENT",O.Length(255))

    def post = foreignKey("FK_POST", postId, postRepo.PostTable)(_.id, onDelete = ForeignKeyAction.Cascade)
    //def user = foreignKey("FK_USER", userId, userRepo.UserTable)(_.id, onDelete = ForeignKeyAction.NoAction)
    def * = (id, commentAuthor, postId, comment).mapTo[Comment]

  }

  lazy val CommentTable = TableQuery[CommentTable]
  def createCommentSchema = CommentTable.schema.createIfNotExists

  def all() = db.run(CommentTable.result)
  def getCommentsInPost(targetPostId: Long) = db.run(CommentTable.filter(_.postId === targetPostId).result)
  def insert(newComment: Comment) = db.run(CommentTable += newComment)
  
  
}
