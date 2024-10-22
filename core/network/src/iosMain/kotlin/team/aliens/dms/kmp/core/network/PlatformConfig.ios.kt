package team.aliens.dms.kmp.core.network

import platform.Foundation.NSBundle
import team.aliens.dms.kmp.core.network.exception.CannotFindIOSBaseurlException
import team.aliens.dms.kmp.core.network.exception.CannotFindIOSTermsUrlException

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object PlatformConfig {
    actual val baseUrl: String =
        (NSBundle.mainBundle.objectForInfoDictionaryKey("DEV_BASE_URL") as? String)
            ?: throw CannotFindIOSBaseurlException()

    actual val termsUrl: String =
        (NSBundle.mainBundle.objectForInfoDictionaryKey("TERMS_URL") as? String)
            ?: throw CannotFindIOSTermsUrlException()
}
