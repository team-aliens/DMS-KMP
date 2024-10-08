package team.aliens.dms.kmp.core.domain.usecase.auth

import team.aliens.dms.kmp.core.jwt.datastore.JwtDataStoreDataSource

class GetTokenUseCase(
    private val jwtDataStoreDataSource: JwtDataStoreDataSource,
) {
    suspend operator fun invoke() = runCatching {
        jwtDataStoreDataSource.loadTokens()
    }
}
