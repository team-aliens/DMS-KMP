package team.aliens.dms.kmp.core.domain.usecase.file

import team.aliens.dms.kmp.core.data.file.repository.FileRepository

class UploadFileUseCase(
    private val fileRepository: FileRepository,
) {
    suspend operator fun invoke(presignedUrl: String, file: ByteArray): Result<String> =
        fileRepository.uploadFile(presignedUrl, file)
}
