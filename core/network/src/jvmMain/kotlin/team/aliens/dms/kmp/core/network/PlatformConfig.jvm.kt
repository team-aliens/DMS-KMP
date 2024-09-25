package team.aliens.dms.kmp.core.network

import team.aliens.dms.kmp.core.network.exception.CannotFindJvmBaseurlException

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object PlatformConfig {
    actual val baseUrl: String = System.getenv("") ?: throw CannotFindJvmBaseurlException()
}
