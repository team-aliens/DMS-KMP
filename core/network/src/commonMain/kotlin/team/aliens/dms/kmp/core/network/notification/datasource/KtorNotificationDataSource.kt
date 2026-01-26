package team.aliens.dms.kmp.core.network.notification.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import team.aliens.dms.kmp.core.network.notification.model.request.SubscribeNotificationTopicRequest
import team.aliens.dms.kmp.core.network.notification.model.request.BatchUpdateNotificationTopicRequest
import team.aliens.dms.kmp.core.network.notification.model.request.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.kmp.core.network.notification.model.request.RegisterFcmDeviceTokenRequest
import team.aliens.dms.kmp.core.network.notification.model.request.UnsubscribeNotificationTopicRequest
import team.aliens.dms.kmp.core.network.notification.model.response.FetchNotificationTopicStatusResponse
import team.aliens.dms.kmp.core.network.notification.model.response.FetchNotificationsResponse

internal class KtorNotificationDataSource(
    private val client: HttpClient,
): NetworkNotificationDataSource {
    override suspend fun registerFcmDeviceToken(request: RegisterFcmDeviceTokenRequest): Result<Unit> =
        runCatching {
            client.post("/notifications/tokens") {
                contentType(ContentType.Application.Json)
                setBody(request.body)
            }
        }

    override suspend fun cancelFcmDeviceTokenRegistration(request: CancelFcmDeviceTokenRegistrationRequest): Result<Unit> =
        runCatching {
            client.delete("/notifications/token") {
                contentType(ContentType.Application.Json)
                setBody(request.body)
            }
        }

    override suspend fun subscribeNotificationTopic(request: SubscribeNotificationTopicRequest): Result<Unit> =
        runCatching {
            client.post("/notifications/topic") {
                contentType(ContentType.Application.Json)
                setBody(request.body)
            }
        }

    override suspend fun unsubscribeNotificationTopic(request: UnsubscribeNotificationTopicRequest): Result<Unit> =
        runCatching {
            client.delete("/notifications/topic") {
                contentType(ContentType.Application.Json)
                setBody(request.body)
            }
        }

    override suspend fun batchUpdateNotificationTopic(request: BatchUpdateNotificationTopicRequest): Result<Unit> =
        runCatching {
            client.patch("/notifications/topic") {
                contentType(ContentType.Application.Json)
                setBody(request.body)
            }
        }

    override suspend fun fetchNotificationTopicStatus(deviceToken: String): Result<FetchNotificationTopicStatusResponse> =
        runCatching {
            client.get("/notifications/topic") {
                parameter("device_token", deviceToken)
            }.body()
        }

    override suspend fun fetchNotifications(): Result<FetchNotificationsResponse> =
        runCatching {
            client.get("/notifications").body()
        }

    override suspend fun updateNotificationReadStatus(notificationId: String): Result<Unit> =
        runCatching {
            client.patch("/notifications/$notificationId/read")
        }

}
