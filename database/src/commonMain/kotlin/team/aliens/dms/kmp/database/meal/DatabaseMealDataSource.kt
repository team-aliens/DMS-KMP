package team.aliens.dms.kmp.database.meal

import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.database.MealEntity

abstract class DatabaseMealDataSource {
    abstract suspend fun queryMeal(date: LocalDate): MealEntity

    abstract suspend fun saveMeal(meal: MealEntity)

    abstract suspend fun saveAllMeals(meals: List<MealEntity>)
}
