package team.aliens.dms.kmp.core.network

import platform.Foundation.NSBundle
import team.aliens.dms.kmp.core.network.exception.CannotFindIOSBaseurlException
import team.aliens.dms.kmp.core.network.exception.CannotFindIOSwebViewUrlException

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object PlatformConfig {
    actual val baseUrl: String =
        (NSBundle.mainBundle.objectForInfoDictionaryKey("DEV_BASE_URL") as? String)
            ?: throw CannotFindIOSBaseurlException()

    actual val webViewUrl: String =
        (NSBundle.mainBundle.objectForInfoDictionaryKey("WEB_VIEW_URL") as? String)
            ?: throw CannotFindIOSwebViewUrlException()
}
