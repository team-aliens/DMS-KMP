package tema.aliens.dms.kmp.feature.remain.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import tema.aliens.dms.kmp.feature.remain.RemainApplicationViewModel

val remainsModule = module {
    viewModelOf(::RemainApplicationViewModel)
}
