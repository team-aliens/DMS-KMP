package team.aliens.dms.kmp.core.domain.usecase.meal

import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.data.meal.repository.MealRepository

class GetMealUseCase(
    private val mealRepository: MealRepository,
) {
    suspend operator fun invoke(date: LocalDate) = mealRepository.getMeal(date = date)
}
