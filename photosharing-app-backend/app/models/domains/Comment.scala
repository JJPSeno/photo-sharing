package models.domains

/* 
 * "COMMENTS"
 * ID: Primary key, auto inc
 * POST_ID: fk -> post id
 * COMMENT: varchar(255)
 */

final case class Comment(
  id: Long,
  commentAuthor: String,
  postId: Long,
  comment: String
)