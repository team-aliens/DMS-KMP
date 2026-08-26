package team.aliens.dms.kmp.core.data.meal.repository

import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.data.meal.mapper.toEntity
import team.aliens.dms.kmp.core.data.meal.mapper.toModel
import team.aliens.dms.kmp.core.database.datasource.meal.MealDatabaseDataSource
import team.aliens.dms.kmp.core.model.meal.MealModel
import team.aliens.dms.kmp.core.network.meal.datasource.NetworkMealDataSource
import team.aliens.dms.kmp.core.network.meal.model.GetMealsRequest

internal class MealRepositoryImpl(
    private val networkMealDataSource: NetworkMealDataSource,
    private val mealDatabaseDataSource: MealDatabaseDataSource,
) : MealRepository {
    override suspend fun getMeal(date: LocalDate): Result<MealModel> {
        return runCatching {
            // 로컬 DB에서 조회 시도
            val cachedMeal = mealDatabaseDataSource.queryMeal(date)
            cachedMeal.toModel()
        }.recoverCatching {
            // 실패 시 네트워크에서 조회 후 DB에 저장하고 해당 날짜 식단 반환
            val meals =
                networkMealDataSource.getMeals(
                    request = GetMealsRequest(path = GetMealsRequest.Path(date = date)),
                ).getOrThrow().toModel()
            mealDatabaseDataSource.saveAllMeals(meals.map { it.toEntity() })
            meals.first { it.date == date }
        }
    }
}
