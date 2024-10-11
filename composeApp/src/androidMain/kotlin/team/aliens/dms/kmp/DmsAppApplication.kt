package team.aliens.dms.kmp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import team.aliens.dms.kmp.di.appModule

class DmsAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule())
            androidContext(this@DmsAppApplication)
        }
    }
}
