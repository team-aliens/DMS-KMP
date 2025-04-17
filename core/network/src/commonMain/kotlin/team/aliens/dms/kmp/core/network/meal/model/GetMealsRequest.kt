package team.aliens.dms.kmp.core.network.meal.model

import kotlinx.datetime.LocalDate

data class GetMealsRequest(
    val path: Path,
) {
    data class Path(
        val date: LocalDate,
    )
}
