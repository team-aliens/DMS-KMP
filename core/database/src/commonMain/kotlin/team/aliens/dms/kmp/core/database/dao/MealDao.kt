package team.aliens.dms.kmp.core.database.dao

import team.aliens.dms.kmp.core.database.DmsDatabase
import team.aliens.dms.kmp.core.database.MealEntity
import team.aliens.dms.kmp.core.database.dmsDispatchers

class MealDao(
    private val dmsDatabase: DmsDatabase,
) {
    private val query get() = dmsDatabase.mealEntityQueries

    suspend fun queryMeal(date: String) = with(dmsDispatchers.io) {
        query.selectMealByDate(date).executeAsOne()
    }

    suspend fun saveMeal(meal: MealEntity) = with(dmsDispatchers.io) {
        query.insertMeal(meal)
    }

    suspend fun saveAllMeals(meals: List<MealEntity>) = with(dmsDispatchers.io) {
        query.transaction {
            meals.forEach { meal ->
                afterRollback { throw Exception("Failed to inserting prayers info") }
                query.insertMeal(meal)
            }
        }
    }
}
