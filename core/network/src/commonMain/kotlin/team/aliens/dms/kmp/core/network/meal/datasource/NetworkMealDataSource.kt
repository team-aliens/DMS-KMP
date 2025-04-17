package team.aliens.dms.kmp.core.network.meal.datasource

import team.aliens.dms.kmp.core.network.meal.model.GetMealsRequest
import team.aliens.dms.kmp.core.network.meal.model.GetMealsResponse

interface NetworkMealDataSource {
    suspend fun getMeals(request: GetMealsRequest): Result<GetMealsResponse>
}
