package team.aliens.dms.kmp.core.data.file.repository

import team.aliens.dms.kmp.core.network.file.datasource.NetworkFileDataSource

internal class FileRepositoryImpl(
    private val networkFileDataSource: NetworkFileDataSource,
) : FileRepository {

    override suspend fun fetchPresignedUrl(fileName: String): Result<String> =
        networkFileDataSource.fetchPresignedUrl(fileName).map { it.fileUploadUrl }

    override suspend fun uploadFile(presignedUrl: String, file: ByteArray): Result<String> =
        networkFileDataSource.uploadFile(presignedUrl, file).map { it.fileUrl }
}
