package team.aliens.dms.kmp.core.network.exception

internal sealed class TokenReissueException(message: String?) : RuntimeException(message)

internal class CannotReissueTokenException : TokenReissueException("Cannot reissue token")
