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
import scala.reflect.io.File

case class FullPost(
  id: Long,
  message: String,
  postAuthor: String,
  commentList: Seq[Comment],
  //photoExtension: String,
  //photo: Array[Byte]
)


@Singleton
class FullPostService @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider, 
  val postRepo: PostRepo, val commRepo: CommentRepo, val photoRepo: PhotoRepo, val userRepo: UserRepo,
)
(implicit executionContext: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  // def createSchemas ={ 

  //   db.run(
  //     userRepo.createUserSchema andThen
  //     postRepo.createPostSchema andThen
  //     photoRepo.createPhotoSchema andThen
  //     commRepo.createCommentSchema 
  //   )

  // }

  def checkPostSchema = postRepo.all()
  def checkPhotoSchema = photoRepo.all()
  def checkCommentSchema = commRepo.all()
  def checkUserSchema = userRepo.all()

  def registerUser(regUsername: String, regPassword: String) = {
    val newUser = User(username = regUsername, password = regPassword)
    userRepo.insert(newUser)
  }

  def validateUser(loginUsername: String, loginPassword: String) = {
    userRepo.getUser(loginUsername)
  }

  def getFullPostv1: Future[Seq[FullPost]] =
    for {
    ps <- db.run {
      (for {
        photos <- photoRepo.PhotoTable
        posts <- photos.post
      } yield (posts, photos)).result 
    }
    ids = ps.map(_._1.id)
    comments <- db.run(commRepo.CommentTable.filter(_.postId inSetBind(ids)).result)
  } yield {
    ps.map { case (post, photo) =>
      println("before full post"+(post, photo))
      FullPost(
        post.id,
        post.message,
        post.postAuthor,
        comments.filter(_.postId == post.id),
        //photo.photoType,
        //photo.photoBytes
      )
    }
  }

  def getFullPostv2 = {
    def postQ = postRepo.PostTable
    def photoQ = photoRepo.PhotoTable
    def commQ = commRepo.CommentTable

    val fullPostQuery = for {
      photos <- photoQ
      posts <- photos.post
    } yield (posts, photos)

    db.run(fullPostQuery.result).map{ fp => fp.map{ case (post, photo) => 
         println("before full post"+(post, photo))
          FullPost(
            post.id,
            post.message,
            post.postAuthor,
      //       comments.filter(_.postId == post.id).map(_.comment),
            Seq.empty[Comment],
            //photo.photoType,
            //photo.photoBytes
        )
      }
    }

  //   for {
  //   ps <- db.run {
  //     (for {
  //       photos <- photoRepo.PhotoTable
  //       posts <- photos.post
  //     } yield (posts, photos)).result 
  //   }
  //   ids = ps.map(_._1.id)
  //   comments <- db.run(commRepo.CommentTable.filter(_.postId inSetBind(ids)).result)
  // } yield {
  //   ps.map { case (post, photo) =>
  //     println("before full post"+(post, photo))
  //     FullPost(
  //       post.id,
  //       post.caption,
  //       post.description,
  ////       comments.filter(_.postId == post.id).map(_.comment),
  //       Seq.empty[String],
  //       photo.photoBytes
  //     )
  //   }
  //  }
  }

  def addFullPost(newPost: Post, newPhoto: Photo) = {
    println("addfullposthit")
    postRepo.insert(newPost).map{ insertedPostId => 
      photoRepo.insert(newPhoto.copy(newPhoto.id, postId = insertedPostId, newPhoto.photoType, newPhoto.photoBytes))
    }
  }

  def addComment(comment: Comment, targetPostId: Long) = {
    val commentToInsert = comment.copy(postId = targetPostId)
    commRepo.insert(commentToInsert)
  }

  def getPhotoFromRepo(id: Long) = {
    photoRepo.getPhotoInPost(id)
  }

//   def checkSchemas ={ db.run(for {
//         photos <- photoRepo.PhotoTable
//         posts <- photos.post
//   } yield (posts, photos)).result
// }
//getFullPost
  //   db.run(
  //   postRepo.PostTable.join(photoRepo.PhotoTable).on(_.id === _.postId)
  //     .joinLeft(commRepo.CommentTable).on {
  //       case ((posts, _), comments) => posts.id === comments.postId
  //     }.result
  // ).map { posts=>
  //   posts.map {
  //     case ((post, photo), comment)  =>
  //         FullPost()
  //   }
    
  // }

  

  // def getAllFullPosts(): Future[Seq[FullPost]] = {
  //   postRepo.all.flatMap(
  //     posts => Future.sequence(posts.map(
  //       post =>
  //         buildFullPost(post.id)
  //     ))
  //   )
  // }

  // def buildFullPost(id: Long): Future[FullPost] = {
  //   for {
  //     posts <- postRepo.getPost(id)
  //     result <- Future.sequence(posts.map { post =>
  //       buildCommentList(post.id).map { comments =>
  //         photoRepo.getPhotoInPost(post.id)
  //           .map { photo => 
  //             FullPost(post.id, post.caption, post.description, comments, photo)
  //           }
  //       }
  //     })
  //   } yield result
    // postRepo.getPost(id).flatMap{
    //   posts => posts.flatMap{
    //     post => buildCommentList(post.id).flatMap{
    //         comments => 
    //           photoRepo.getPhotoInPost(post.id).flatMap{
    //             photo => FullPost(post.id, post.caption, post.description, comments, photo)
    //           }
    //       }
    //   }
    // }
  // }

  // def buildCommentList(id: Long): Future[List[Comment]] = {
  //   commRepo.getCommentsInPost(id).map{
  //     comments => (for(
  //       c <- comments
  //     ) yield c).toList
  //   }
  // }

  // def getAllPosts() = ???

}
