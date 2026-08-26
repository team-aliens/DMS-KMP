package team.aliens.dms.kmp.core.database.adapter

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDate

internal val dateAdapter =
    object : ColumnAdapter<LocalDate, String> {
        override fun decode(databaseValue: String): LocalDate = LocalDate.parse(databaseValue)

        override fun encode(value: LocalDate): String = value.toString()
    }
