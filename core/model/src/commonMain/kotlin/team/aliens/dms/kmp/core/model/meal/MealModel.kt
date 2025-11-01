package team.aliens.dms.kmp.core.model.meal

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.util.today

@Serializable
data class MealModel(
    val date: LocalDate = today,
    val breakfast: List<String> = emptyList(),
    val kcalBreakfast: String? = null,
    val lunch: List<String> = emptyList(),
    val kcalLunch: String? = null,
    val dinner: List<String> = emptyList(),
    val kcalDinner: String? = null,
)
