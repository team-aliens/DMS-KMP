package team.aliens.dms.kmp.core.data.file.repository

interface FileRepository {
    suspend fun fetchPresignedUrl(fileName: String): Result<String>

    suspend fun uploadFile(
        presignedUrl: String,
        file: ByteArray,
    ): Result<String>
}
