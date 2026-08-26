package team.aliens.dms.kmp.core.data.meal.mapper

import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.database.MealEntity
import team.aliens.dms.kmp.core.model.meal.MealModel
import team.aliens.dms.kmp.core.network.meal.model.GetMealsResponse

internal fun GetMealsResponse.toModel() = this.meals.map { it.toModel() }

internal fun MealEntity.toModel() =
    MealModel(
        date = this.date,
        breakfast = this.breakfast,
        kcalBreakfast = this.kcal_breakfast,
        lunch = this.lunch,
        kcalLunch = this.kcal_lunch,
        dinner = this.dinner,
        kcalDinner = this.kcal_dinner,
    )

internal fun MealModel.toEntity() =
    MealEntity(
        date = this.date,
        breakfast = this.breakfast,
        kcal_breakfast = this.kcalBreakfast,
        lunch = this.lunch,
        kcal_lunch = this.kcalLunch,
        dinner = this.dinner,
        kcal_dinner = this.kcalDinner,
    )

private fun GetMealsResponse.Meal.toModel() =
    MealModel(
        date = LocalDate.parse(this.date),
        breakfast = this.breakfast.dropLast(1),
        kcalBreakfast = this.breakfast.last(),
        lunch = this.lunch.dropLast(1),
        kcalLunch = this.lunch.last(),
        dinner = this.dinner.dropLast(1),
        kcalDinner = this.dinner.last(),
    )
