# DMS-KMP: Firebase Cloud Messaging (FCM) 구현 가이드

## **1. 개요**

본 문서는 DMS-KMP (Kotlin Multiplatform) 프로젝트에 Firebase Cloud Messaging을 연동하여 Android와 iOS 플랫폼에서 푸시 알림을 수신하고 처리하는 방법을 안내합니다. KMP 아키텍처에 맞춰 `expect`/`actual` 패턴을 사용하고, 플랫폼별 종속성을 각 OS에 맞게 구현합니다.

---

## **2. `core/notification` 모듈 생성 및 설정**

알림 관련 로직을 독립적으로 관리하기 위해 `core` 내에 `notification` 모듈을 새로 생성합니다.

**2.1. `settings.gradle.kts`에 모듈 추가**

```kotlin
// settings.gradle.kts
include(
    // ...,
    ":core:notification",
)
```

**2.2. `core/notification/build.gradle.kts` 파일 생성**

```kotlin
// core/notification/build.gradle.kts
plugins {
    id("dms.kmp.library")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.domain)
                implementation(projects.core.data)
            }
        }
        val androidMain by getting {
            dependencies {
                // Firebase
                implementation(platform(libs.firebase.bom))
                implementation(libs.firebase.messaging)

                // Androidx Notification
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.appcompat)
            }
        }
        val iosMain by getting {
            // iOS 의존성은 없음
        }
    }
}

android {
    namespace = "team.aliens.dms.kmp.core.notification"
}
```
*참고: `dms.kmp.library` 플러그인이 이미 필요한 설정을 포함하고 있다고 가정합니다. `libs.androidx.core.ktx` 등은 `libs.versions.toml`에 미리 정의되어 있어야 합니다. 아래 부분은 예시입니다. 해당 프로젝트와 호환되는 라이브러리 버전을 가져와야합니다.*

**2.3. `gradle/libs.versions.toml`에 의존성 추가**

```toml
# gradle/libs.versions.toml
[versions]
firebaseBom = "32.7.4"
firebaseMessaging = "23.4.1"

[libraries]
firebase-bom = { group = "com.google.firebase", name = "firebase-bom", version.ref = "firebaseBom" }
firebase-messaging = { group = "com.google.firebase", name = "firebase-messaging-ktx", version.ref = "firebaseMessaging" }
```

---

## **3. 공통 레이어 구현 (`commonMain`)**

**3.1. `expect` 인터페이스 정의**

`core/notification/src/commonMain/kotlin/team/aliens/dms/kmp/core/notification` 경로에 아래 파일들을 생성합니다.

**`DeviceTokenManager.kt`**
```kotlin
package team.aliens.dms.kmp.core.notification

expect class DeviceTokenManager {
    suspend fun fetchDeviceToken()
    fun onNewToken(token: String)
}
```

**`NotificationManager.kt`**
```kotlin
package team.aliens.dms.kmp.core.notification

expect class NotificationManager {
    fun sendNotification(
        title: String,
        body: String,
    )
}
```

**3.2. UseCase 및 Repository 업데이트**

*   `core/domain`에 `SaveDeviceTokenUseCase`를 생성합니다.
*   `core/data`의 `UserRepository`를 수정합니다.

**`core/domain/src/commonMain/kotlin/team/aliens/dms/kmp/core/domain/usecase/user/SaveDeviceTokenUseCase.kt`**
```kotlin
package team.aliens.dms.kmp.core.domain.usecase.user

import team.aliens.dms.kmp.core.data.repository.UserRepository

class SaveDeviceTokenUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(deviceToken: String) = runCatching {
        userRepository.saveDeviceToken(deviceToken = deviceToken)
    }
}
```

**`core/data/src/commonMain/kotlin/team/aliens/dms/kmp/core/data/repository/UserRepository.kt`**
```kotlin
package team.aliens.dms.kmp.core.data.repository

interface UserRepository {
    // ... 기존 메서드
    suspend fun saveDeviceToken(deviceToken: String)
}
```

**`core/data/src/commonMain/kotlin/team/aliens/dms/kmp/core/data/repository/UserRepositoryImpl.kt`**
```kotlin
package team.aliens.dms.kmp.core.data.repository

import team.aliens.dms.kmp.core.data.datasource.LocalUserDataSource // LocalUserDataSource import
// ...
class UserRepositoryImpl(
    private val localUserDataSource: LocalUserDataSource,
    // ...
) : UserRepository {
    // ... 기존 메서드
    override suspend fun saveDeviceToken(deviceToken: String) {
        localUserDataSource.saveDeviceToken(deviceToken)
    }
}
```
*참고: `LocalUserDataSource`에 `saveDeviceToken` 메서드를 추가해야 합니다. (예: `multiplatform-settings` 사용)*

---

## **4. Android 플랫폼 구현**

**4.1. Gradle 설정 및 `google-services.json` 추가**

**`composeApp/build.gradle.kts`**
```kotlin
plugins {
    // ...
    id("com.google.gms.google-services")
}

dependencies {
    // ...
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(projects.core.notification)
}
```
*   **`google-services.json` 파일을 `composeApp/` 디렉터리에 복사합니다.**

**4.2. AndroidManifest.xml 설정**

`composeApp/src/androidMain/AndroidManifest.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

    <application>
        <!-- ... -->
        <service
            android:name=".service.DmsMessagingService"
            android:exported="false">
            <intent-filter>
                <action android:name="com.google.firebase.MESSAGING_EVENT" />
            </intent-filter>
        </service>

        <meta-data
            android:name="com.google.firebase.messaging.default_notification_icon"
            android:resource="@drawable/ic_notification" />

        <meta-data
            android:name="com.google.firebase.messaging.default_notification_color"
            android:resource="@color/primary" />
    </application>
</manifest>
```
*참고: `ic_notification.xml`과 `primary` 색상 리소스가 `androidMain`의 `res` 폴더에 정의되어 있어야 합니다.*

**4.3. `actual` 구현**

`core/notification/src/androidMain/kotlin/team/aliens/dms/kmp/core/notification` 경로에 아래 파일들을 생성합니다.

**`DeviceTokenManager.kt`**
```kotlin
package team.aliens.dms.kmp.core.notification

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import team.aliens.dms.kmp.core.domain.usecase.user.SaveDeviceTokenUseCase

actual class DeviceTokenManager(
    private val saveDeviceTokenUseCase: SaveDeviceTokenUseCase,
) {
    actual suspend fun fetchDeviceToken() {
        val token = FirebaseMessaging.getInstance().token.await()
        onNewToken(token)
    }

    actual fun onNewToken(token: String) {
        // CoroutineScope을 사용하여 UseCase 호출 (DI를 통해 적절한 Scope 주입 권장)
        // GlobalScope.launch { saveDeviceTokenUseCase(token) }
    }
}
```

**`NotificationManager.kt`**
```kotlin
package team.aliens.dms.kmp.core.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager as AndroidNotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import team.aliens.dms.kmp.core.designsystem.R as DesignSystemR

actual class NotificationManager(
    private val context: Context,
) {
    private val notificationManager = NotificationManagerCompat.from(context)

    actual fun sendNotification(title: String, body: String) {
        createNotificationChannel()

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(DesignSystemR.drawable.ic_notification) // 예시 아이콘
            .setColor(context.getColor(DesignSystemR.color.primary)) // 예시 색상
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, AndroidNotificationManager.IMPORTANCE_HIGH)
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "dms"
        private const val CHANNEL_NAME = "dms"
    }
}
```

**4.4. `DmsMessagingService.kt` 구현**

`composeApp/src/androidMain/kotlin/team/aliens/dms/kmp/service` 경로에 생성합니다.

```kotlin
package team.aliens.dms.kmp.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import team.aliens.dms.kmp.core.notification.DeviceTokenManager
import team.aliens.dms.kmp.core.notification.NotificationManager

class DmsMessagingService : FirebaseMessagingService() {
    private val deviceTokenManager: DeviceTokenManager by inject()
    private val notificationManager: NotificationManager by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            deviceTokenManager.onNewToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.run {
            notificationManager.sendNotification(
                title = title ?: "DMS",
                body = body ?: "새로운 알림이 도착했습니다.",
            )
        }
    }
}
```

---

## **5. iOS 플랫폼 구현**

**5.1. Cocoapods 설정 및 `GoogleService-Info.plist` 추가**

**`composeApp/build.gradle.kts`** 의 `cocoapods` 블록
```kotlin
kotlin {
    //...
    cocoapods {
        //...
        pod("FirebaseMessaging")
    }
}
```
*   `./gradlew podInstall` 실행
*   **`GoogleService-Info.plist` 파일을 `iosApp/iosApp` 디렉터리에 복사하고 Xcode에서 "Add Files to 'iosApp'"을 통해 프로젝트에 추가합니다.**

**5.2. `AppDelegate` 설정**

`iosApp/iosApp/iOSApp.swift` (또는 별도의 `AppDelegate.swift`)
```swift
import SwiftUI
import Firebase
import UserNotifications

// KMP 공통 모듈에 접근하기 위한 Helper
// 이 Helper는 iosMain 소스셋에서 미리 정의되어야 합니다.
import composeApp // 프로젝트 이름에 따라 변경될 수 있습니다.

class AppDelegate: NSObject, UIApplicationDelegate, UNUserNotificationCenterDelegate, MessagingDelegate {

    // Koin에서 주입받은 DeviceTokenManager
    private lazy var deviceTokenManager: DeviceTokenManager = {
        return KoinIos.shared.getDeviceTokenManager()
    }()

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        FirebaseApp.configure()

        UNUserNotificationCenter.current().delegate = self
        Messaging.messaging().delegate = self

        // 알림 권한 요청
        let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
        UNUserNotificationCenter.current().requestAuthorization(options: authOptions) { _, _ in }
        application.registerForRemoteNotifications()

        return true
    }

    // FCM 토큰 수신
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        if let token = fcmToken {
            print("FCM registration token: \(token)")
            deviceTokenManager.onNewToken(token: token)
        }
    }

    // APNs 토큰을 FCM 토큰으로 매핑
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
    }

    // 포그라운드에서 알림 수신
    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification,
        withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void
    ) {
        completionHandler([[.banner, .sound, .badge]])
    }
}

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    // Koin 초기화
    init() {
        KoinIos.doInit()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
```

**5.3. Swift-KMP 브릿지 및 `actual` 구현**

`core/notification/src/iosMain/kotlin/...` 경로에 파일들을 생성합니다.

**`KoinIos.kt` (예시 - DI Helper)**
```kotlin
// composeApp/src/iosMain/kotlin/team/aliens/dms/kmp/di/KoinIos.kt
package team.aliens.dms.kmp.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import team.aliens.dms.kmp.core.notification.DeviceTokenManager
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            // 여기에 main Koin 모듈을 추가합니다.
            // 예: commonModule, featureModule 등
        )
    }
}

// Swift에서 Koin 객체를 가져오기 위한 Helper
object KoinIos: KoinComponent {
    fun doInit() = initKoin() // Swift에서 호출할 초기화 함수
    fun getDeviceTokenManager(): DeviceTokenManager {
        val deviceTokenManager: DeviceTokenManager by inject()
        return deviceTokenManager
    }
    // 필요한 다른 KMP 객체들도 여기에 추가할 수 있습니다.
}
```

**`DeviceTokenManager.kt` (`actual`)**
```kotlin
package team.aliens.dms.kmp.core.notification

import team.aliens.dms.kmp.core.domain.usecase.user.SaveDeviceTokenUseCase
// kotlinx.coroutines.GlobalScope, kotlinx.coroutines.launch 등 코루틴 관련 import 필요

actual class DeviceTokenManager(
    private val saveDeviceTokenUseCase: SaveDeviceTokenUseCase,
) {
    // iOS에서는 fetchDeviceToken을 직접 호출하지 않고, AppDelegate에서 토큰을 받음
    actual suspend fun fetchDeviceToken() {
        // No-op
    }

    actual fun onNewToken(token: String) {
        // CoroutineScope을 사용하여 UseCase 호출
        // GlobalScope.launch { saveDeviceTokenUseCase(token) }
    }
}
```

**`NotificationManager.kt` (`actual`)**
```kotlin
package team.aliens.dms.kmp.core.notification

import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNUserNotificationCenter

actual class NotificationManager {
    private val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()

    actual fun sendNotification(title: String, body: String) {
        // iOS에서는 보통 서버에서 직접 APNs를 통해 알림을 보내므로,
        // 클라이언트에서 직접 보내는 경우는 포그라운드 처리 등에 한정됨.
        // 아래는 로컬 알림을 생성하는 예시.
        val content = UNMutableNotificationContent().apply {
            setTitle(title)
            setBody(body)
        }
        val request = UNNotificationRequest.requestWithIdentifier(
            identifier = "dms_local_notification",
            content = content,
            trigger = null,
        )
        notificationCenter.addNotificationRequest(request, null)
    }
}
```

---

## **6. DI (Koin) 모듈 설정**

**`core/notification/src/commonMain/kotlin/team/aliens/dms/kmp/core/notification/di/NotificationModule.kt`**
```kotlin
package team.aliens.dms.kmp.core.notification.di

import org.koin.core.module.Module
import org.koin.dsl.module
import team.aliens.dms.kmp.core.notification.DeviceTokenManager
import team.aliens.dms.kmp.core.notification.NotificationManager
import team.aliens.dms.kmp.core.domain.usecase.user.SaveDeviceTokenUseCase // UseCase import

expect val notificationModule: Module

val notificationCommonModule = module {
    factory { SaveDeviceTokenUseCase(get()) } // SaveDeviceTokenUseCase 주입
}
```

**`core/notification/src/androidMain/kotlin/team/aliens/dms/kmp/core/notification/di/NotificationModule.kt`**
```kotlin
package team.aliens.dms.kmp.core.notification.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.notification.DeviceTokenManager
import team.aliens.dms.kmp.core.notification.NotificationManager
import android.content.Context
import org.koin.android.ext.koin.androidApplication

actual val notificationModule = module {
    includes(notificationCommonModule)
    single { DeviceTokenManager(get()) }
    single { NotificationManager(androidApplication() as Context) }
}
```

**`core/notification/src/iosMain/kotlin/team/aliens/dms/kmp/core/notification/di/NotificationModule.kt`**
```kotlin
package team.aliens.dms.kmp.core.notification.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.notification.DeviceTokenManager
import team.aliens.dms.kmp.core.notification.NotificationManager

actual val notificationModule = module {
    includes(notificationCommonModule)
    single { DeviceTokenManager(get()) }
    single { NotificationManager() }
}
```

마지막으로, `composeApp`의 메인 DI 모듈(`featureModule` 등)에 `notificationModule`을 추가합니다.

**`composeApp/src/commonMain/kotlin/team/aliens/dms/kmp/di/FeatureModule.kt`**
```kotlin
package team.aliens.dms.kmp.di

import org.koin.dsl.module
import resetpassword.di.resetPasswordModule
import team.aliens.dms.kmp.core.domain.usecase.notification.di.notificationModule
import team.aliens.dms.kmp.feature.application.di.applicationModule
import team.aliens.dms.kmp.feature.findid.di.findIdModule
import team.aliens.dms.kmp.feature.home.di.homeModule
import team.aliens.dms.kmp.feature.meal.di.mealModule
import team.aliens.dms.kmp.feature.mypage.di.myPageModule
import team.aliens.dms.kmp.feature.notice.di.noticeModule
import team.aliens.dms.kmp.feature.point.di.pointsModule
import team.aliens.dms.kmp.feature.profile.di.profileModule
import team.aliens.dms.kmp.feature.signin.di.signInModule
import team.aliens.dms.kmp.feature.signup.di.signUpModule
import team.aliens.dms.kmp.feature.splash.di.splashModule
import team.aliens.dms.kmp.feature.volunteer.di.volunteerModule
import team.aliens.dms.kmp.feature.setting.di.settingModule
import team.aliens.dms.kmp.feature.vote.di.voteModule
import tema.aliens.dms.kmp.feature.remain.di.remainsModule
import team.aliens.dms.kmp.core.notification.di.notificationModule // notificationModule import

internal val featureModule = module {
    includes(
        splashModule,
        signInModule,
        findIdModule,
        resetPasswordModule,
        signUpModule,
        homeModule,
        applicationModule,
        noticeModule,
        myPageModule,
        remainsModule,
        voteModule,
        volunteerModule,
        pointsModule,
        mealModule,
        notificationModule, // 여기에 추가
        settingModule,
        profileModule,
    )
}