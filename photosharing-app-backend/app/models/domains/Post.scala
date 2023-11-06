package models.domains

/* 
 * "POSTS"
 * ID: Primary key, auto inc
 * CAPTION: varchar(50)
 * DESCRIPTION: varchar(255)
 */

final case class Post(
  message: String,
  postAuthor: String,
  userId: Long,
  id: Long = 0L,
)
