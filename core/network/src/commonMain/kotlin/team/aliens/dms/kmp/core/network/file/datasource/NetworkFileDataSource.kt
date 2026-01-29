package team.aliens.dms.kmp.core.network.file.datasource

import team.aliens.dms.kmp.core.network.file.model.response.FetchFileUrlResponse
import team.aliens.dms.kmp.core.network.file.model.response.FetchPresignedUrlResponse

interface NetworkFileDataSource {
    suspend fun fetchPresignedUrl(fileName: String): Result<FetchPresignedUrlResponse>
    suspend fun uploadFile(presignedUrl: String, file: ByteArray): Result<FetchFileUrlResponse>
}
