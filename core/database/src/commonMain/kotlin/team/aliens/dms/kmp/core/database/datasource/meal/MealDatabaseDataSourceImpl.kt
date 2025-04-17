package team.aliens.dms.kmp.core.database.datasource.meal

import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.database.MealEntity
import team.aliens.dms.kmp.core.database.dao.MealDao

internal class MealDatabaseDataSourceImpl(
    private val mealDao: MealDao,
) : MealDatabaseDataSource() {
    override suspend fun queryMeal(date: LocalDate): MealEntity = mealDao.queryMeal(date)

    override suspend fun saveMeal(meal: MealEntity) = mealDao.saveMeal(meal)

    override suspend fun saveAllMeals(meals: List<MealEntity>) = mealDao.saveAllMeals(meals)
}
