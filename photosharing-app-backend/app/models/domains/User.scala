package models.domains

/* 
 * "USERS"
 * username: Primary key, unique, varchar(50)
 * password: varchar(255)
 */

final case class User(
  id: Long = 0L,
  username: String,
  password: String
)
