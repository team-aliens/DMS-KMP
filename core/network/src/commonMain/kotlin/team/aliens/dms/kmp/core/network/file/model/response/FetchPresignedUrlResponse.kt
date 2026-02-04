package team.aliens.dms.kmp.core.network.file.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchPresignedUrlResponse(
    @SerialName("file_upload_url")
    val fileUploadUrl: String,
)
