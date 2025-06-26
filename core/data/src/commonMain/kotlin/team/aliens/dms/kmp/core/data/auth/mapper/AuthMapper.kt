package team.aliens.dms.kmp.core.data.auth.mapper

import kotlinx.datetime.LocalDateTime
import team.aliens.dms.kmp.core.model.auth.AccessToken
import team.aliens.dms.kmp.core.model.auth.EmailModel
import team.aliens.dms.kmp.core.model.auth.RefreshToken
import team.aliens.dms.kmp.core.model.auth.TokenModel
import team.aliens.dms.kmp.core.network.auth.model.TokensResponse
import team.aliens.dms.kmp.core.network.auth.model.dto.TokenDto
import team.aliens.dms.kmp.core.network.auth.model.response.CheckIdExistsResponse

internal fun CheckIdExistsResponse.toModel() = EmailModel(
    email = email,
)

internal fun TokensResponse.toModel(): TokenModel = TokenModel(
    accessToken = AccessToken(
        value = accessToken,
        expiration = LocalDateTime.parse(accessTokenExpiration),
    ),
    refreshToken = RefreshToken(
        value = refreshToken,
        expiration = LocalDateTime.parse(refreshTokenExpiration),
    ),
)

internal fun TokenDto.toModel() = TokenModel(
    accessToken = AccessToken(
        value = accessToken,
        expiration = LocalDateTime.parse(accessTokenExpiredAt),
    ),
    refreshToken = RefreshToken(
        value = refreshToken,
        expiration = LocalDateTime.parse(refreshTokenExpiredAt),
    ),
)
