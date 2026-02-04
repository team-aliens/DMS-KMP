package team.aliens.dms.kmp.core.domain.usecase.file

import team.aliens.dms.kmp.core.data.file.repository.FileRepository

class FetchPresignedUrlUseCase(
    private val fileRepository: FileRepository,
) {
    suspend operator fun invoke(fileName: String): Result<String> =
        fileRepository.fetchPresignedUrl(fileName)
}
