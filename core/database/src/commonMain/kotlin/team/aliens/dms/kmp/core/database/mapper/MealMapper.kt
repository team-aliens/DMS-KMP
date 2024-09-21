package team.aliens.dms.kmp.core.database.mapper

import team.aliens.dms.kmp.core.database.MealEntity
import team.aliens.dms.kmp.core.database.model.Meal

internal fun Meal.toEntity() = MealEntity(
    date = this.date,
    breakfast = this.breakfast,
    kcal_breakfast = this.kcalOfBreakfast,
    lunch = this.lunch,
    kcal_lunch = this.kcalOfLunch,
    dinner = this.dinner,
    kcal_dinner = this.kcalOfDinner,
)
