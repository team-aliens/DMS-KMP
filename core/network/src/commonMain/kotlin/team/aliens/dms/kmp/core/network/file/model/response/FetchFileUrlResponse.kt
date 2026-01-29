package team.aliens.dms.kmp.core.network.file.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchFileUrlResponse(
    @SerialName("file_url")
    val fileUrl: String,
)
