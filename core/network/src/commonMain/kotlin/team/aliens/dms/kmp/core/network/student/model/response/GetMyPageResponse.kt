package team.aliens.dms.kmp.core.network.student.model.response

import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.network.student.model.dto.GenderTypeDto

@Serializable
data class GetMyPageResponse(
    val schoolName: String,
    val name: String,
    val gcn: String,
    val profileImageUrl: String,
    val sex: GenderTypeDto,
    val bonusPoint: Int,
    val minusPoint: Int,
    val phrase: String,
)
