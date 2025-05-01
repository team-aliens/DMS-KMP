package team.aliens.dms.kmp.core.model.mypage

import team.aliens.dms.kmp.core.model.type.GenderType

data class MyPageModel (
    val schoolName: String = "",
    val name: String = "",
    val gcn: String = "",
    val profileImageUrl: String? = null,
    val sex: GenderType = GenderType.ALL,
    val bonusPoint: Int = 0,
    val minusPoint: Int = 0,
    val phrase: String = "",
)
