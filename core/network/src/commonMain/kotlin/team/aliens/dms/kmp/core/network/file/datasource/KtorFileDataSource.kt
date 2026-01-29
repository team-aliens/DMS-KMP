package team.aliens.dms.kmp.core.network.file.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import team.aliens.dms.kmp.core.network.file.model.response.FetchFileUrlResponse
import team.aliens.dms.kmp.core.network.file.model.response.FetchPresignedUrlResponse

internal class KtorFileDataSource(
    private val client: HttpClient,
) : NetworkFileDataSource {

    override suspend fun fetchPresignedUrl(fileName: String): Result<FetchPresignedUrlResponse> =
        kotlin.runCatching {
            client.get("/files/url") {
                parameter("file_name", fileName)
            }.body()
        }

    override suspend fun uploadFile(
        presignedUrl: String,
        file: ByteArray,
    ): Result<FetchFileUrlResponse> =
        kotlin.runCatching {
            client.put(presignedUrl) {
                contentType(ContentType.Application.OctetStream)
                setBody(file)
            }.body()
        }
}
