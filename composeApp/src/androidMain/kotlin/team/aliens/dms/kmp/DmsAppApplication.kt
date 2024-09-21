package team.aliens.dms.kmp

import android.app.Application
import team.aliens.dms.kmp.di.initKoin

class DmsAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()
    }
}
