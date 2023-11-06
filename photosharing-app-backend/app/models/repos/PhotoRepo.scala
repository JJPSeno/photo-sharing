package models.repos

import models.domains._
import javax.inject._
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PhotoRepo @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider, val postRepo: PostRepo)
(implicit executionContext: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  protected class PhotoTable(tag: Tag) extends Table[Photo](tag, "PHOTOS"){
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def postId = column[Long]("POST_ID")
    def photoType = column[String]("PHOTO_TYPE", O.Length(10))
    def photoBytes = column[Array[Byte]]("PHOTO_BYTES", O.SqlType("BINARY(MAX)"))

    def post = foreignKey("FK_POST", postId, postRepo.PostTable)(_.id, onDelete = ForeignKeyAction.Cascade)
    
    def * = (id, postId, photoType, photoBytes).mapTo[Photo]

  }

  lazy val PhotoTable = TableQuery[PhotoTable]
  def createPhotoSchema = PhotoTable.schema.createIfNotExists

  def all() = db.run(PhotoTable.result)
  def getPhotoInPost(targetPostId: Long) = db.run(PhotoTable.filter(_.postId === targetPostId).result.head)
  def insert(newPhoto: Photo) = { 
    println("inserting photo...")
    db.run(PhotoTable += newPhoto).map( _ => println("photo insert done"))
  }
  
  
}
