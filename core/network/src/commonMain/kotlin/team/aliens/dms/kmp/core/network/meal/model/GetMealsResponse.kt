package team.aliens.dms.kmp.core.network.meal.model

import kotlinx.serialization.Serializable

@Serializable
data class GetMealsResponse(
    val meals: List<Meal>,
) {
    @Serializable
    data class Meal(
        val date: String,
        val breakfast: List<String>,
        val lunch: List<String>,
        val dinner: List<String>,
    )
}
