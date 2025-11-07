package team.aliens.dms.kmp.core.network.schools.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetSchoolVerificationCodeCheckResponse(
    val schoolId: String,
)
