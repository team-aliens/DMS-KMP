package team.aliens.dms.kmp

import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import team.aliens.dms.kmp.core.notification.NotificationManager
import team.aliens.dms.kmp.di.appModule

class DmsAppApplication : Application() {

    private val notificationManager: NotificationManager by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule())
            androidContext(this@DmsAppApplication)
        }

        notificationManager.initializeNotificationChannel()
    }
}
