package team.aliens.dms.kmp.core.network

import platform.Foundation.NSBundle
import team.aliens.dms.kmp.core.network.exception.CannotFindIosBaseurlException
import team.aliens.dms.kmp.core.network.exception.CannotFindIosTermsUrlException

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object PlatformConfig {
    actual val baseUrl: String =
        (NSBundle.mainBundle.objectForInfoDictionaryKey("BaseURL") as? String)
            ?: throw CannotFindIosBaseurlException()
    actual val termsUrl: String =
        (NSBundle.mainBundle.objectForInfoDictionaryKey("TermsUrl") as? String)
            ?: throw CannotFindIosTermsUrlException()
}
