package team.aliens.dms.kmp.core.network

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object PlatformConfig {
    actual val baseUrl: String = BuildConfig.BASE_URL
    actual val webViewUrl: String = BuildConfig.WEB_VIEW_URL
    actual val reportFormUrl: String = BuildConfig.REPORT_FORM_URL
}
