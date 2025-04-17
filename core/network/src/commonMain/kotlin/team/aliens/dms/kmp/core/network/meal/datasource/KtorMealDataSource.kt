package team.aliens.dms.kmp.core.network.meal.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import team.aliens.dms.kmp.core.network.meal.model.GetMealsRequest
import team.aliens.dms.kmp.core.network.meal.model.GetMealsResponse

internal class KtorMealDataSource(
    private val client: HttpClient,
) : NetworkMealDataSource {
    override suspend fun getMeals(request: GetMealsRequest): Result<GetMealsResponse> =
        kotlin.runCatching {
            client.get("/meals/${request.path.date}").body()
        }
}
