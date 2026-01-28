package team.aliens.dms.kmp.core.media.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformMediaModule: Module = module {
    // JVM is not required to access the gallery (used only for Android/iOS)
}
