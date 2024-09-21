package team.aliens.dms.kmp.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DriverFactory {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(DmsDatabase.Schema, "${DatabaseConstants.NAME}.db")
}
