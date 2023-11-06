package models.domains

final case class Photo(
  id: Long,
  postId: Long,
  photoType: String,
  photoBytes: Array[Byte]

)
