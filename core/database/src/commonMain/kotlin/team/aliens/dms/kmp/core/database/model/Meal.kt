package team.aliens.dms.kmp.core.database.model

data class Meal(
    val date: String,
    val breakfast: List<String>,
    val kcalOfBreakfast: String?,
    val lunch: List<String>,
    val kcalOfLunch: String?,
    val dinner: List<String>,
    val kcalOfDinner: String?,
)
