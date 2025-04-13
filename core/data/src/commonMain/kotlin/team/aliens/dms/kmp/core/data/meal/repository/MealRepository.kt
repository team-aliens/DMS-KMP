package team.aliens.dms.kmp.core.data.meal.repository

import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.model.meal.MealModel

interface MealRepository {
    suspend fun getMeal(date: LocalDate): Result<MealModel>
}
