package team.aliens.dms.kmp.core.database.adapter

import app.cash.sqldelight.ColumnAdapter

internal val mealAdapter = object : ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String): List<String> =
        if (databaseValue.isBlank()) {
            emptyList()
        } else {
            databaseValue.split(",,")
        }

    override fun encode(value: List<String>): String =
        value.joinToString(",,")
}
