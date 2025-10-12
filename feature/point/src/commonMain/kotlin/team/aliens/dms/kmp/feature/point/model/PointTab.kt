package team.aliens.dms.kmp.feature.point.model

import team.aliens.dms.kmp.core.model.type.PointType

internal sealed class PointTab(
    val title: String,
    val pointType: PointType,
) {
    data object All : PointTab(title = "전체", pointType = PointType.ALL)
    data object Bonus : PointTab(title = "상점", pointType = PointType.BONUS)
    data object Minus : PointTab(title = "벌점", pointType = PointType.MINUS)
}
