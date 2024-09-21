package team.aliens.dms.kmp.database.meal

import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.database.MealEntity
import team.aliens.dms.kmp.core.database.dao.MealDao

internal class DatabaseMealDataSourceImpl(
    private val mealDao: MealDao,
) : DatabaseMealDataSource() {
    override suspend fun queryMeal(date: LocalDate): MealEntity = mealDao.queryMeal(date.toString())

    override suspend fun saveMeal(meal: MealEntity) = mealDao.saveMeal(meal)

    override suspend fun saveAllMeals(meals: List<MealEntity>) = mealDao.saveAllMeals(meals)
}
