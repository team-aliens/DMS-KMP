package team.aliens.dms.kmp.core.network.notification.model.request

data class RegisterFcmDeviceTokenRequest(
    val body: Body,
) {
    data class Body(
        val deviceToken: String,
    )
}
