package team.aliens.dms.kmp.core.data.auth.mapper

import kotlinx.datetime.LocalDateTime
import team.aliens.dms.kmp.core.datastore.auth.model.AccessToken
import team.aliens.dms.kmp.core.datastore.auth.model.RefreshToken
import team.aliens.dms.kmp.core.datastore.auth.model.Tokens
import team.aliens.dms.kmp.core.model.auth.EmailModel
import team.aliens.dms.kmp.core.network.auth.model.TokensResponse
import team.aliens.dms.kmp.core.network.auth.model.response.CheckIdExistsResponse

internal fun CheckIdExistsResponse.toModel() = EmailModel(
    email = this.email
)

fun TokensResponse.toModel(): Tokens = Tokens(
    accessToken = AccessToken(
        value = this.accessToken,
        expiration = LocalDateTime.parse(this.accessTokenExpiration),
    ),
    refreshToken = RefreshToken(
        value = this.refreshToken,
        expiration = LocalDateTime.parse(this.refreshTokenExpiration),
    ),
)
