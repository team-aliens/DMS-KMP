package team.aliens.dms.kmp.feature.volunteer.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.volunteer.viewmodel.VolunteerViewModel

val volunteerModule = module {
    viewModelOf(::VolunteerViewModel)
}
