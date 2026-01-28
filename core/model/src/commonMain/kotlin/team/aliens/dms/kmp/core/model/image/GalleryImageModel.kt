package team.aliens.dms.kmp.core.model.image

data class GalleryImageModel(
    val id: String,
    val uri: String,
    val dateAdded: Long,
    val width: Int,
    val height: Int,
)
