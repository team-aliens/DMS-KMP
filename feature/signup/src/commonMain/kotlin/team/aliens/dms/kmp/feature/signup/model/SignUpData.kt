package team.aliens.dms.kmp.feature.signup.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class SignUpData(
    val schoolCode: String = "",
    val schoolAnswer: String = "",
    val email: String = "",
    val authCode: String = "",
    val grade: Int = 0,
    val classRoom: Int = 0,
    val number: Int = 0,
    val accountId: String = "",
    val password: String = "",
    val profileImageUrl: String? = null,
)

internal fun SignUpData.toJsonString() = Json.encodeToString(this)
internal fun String.toSignUpData() = Json.decodeFromString<SignUpData>(this)
