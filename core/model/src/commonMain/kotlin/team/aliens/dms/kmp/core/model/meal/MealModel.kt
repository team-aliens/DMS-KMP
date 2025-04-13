package team.aliens.dms.kmp.core.model.meal

import kotlinx.datetime.LocalDate

data class MealModel(
    val date: LocalDate,
    val breakfast: List<String>,
    val kcalBreakfast: String?,
    val lunch: List<String>,
    val kcalLunch: String?,
    val dinner: List<String>,
    val kcalDinner: String?,
)
