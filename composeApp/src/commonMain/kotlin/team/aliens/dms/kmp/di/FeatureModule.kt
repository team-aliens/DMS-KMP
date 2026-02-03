package team.aliens.dms.kmp.di

import org.koin.dsl.module
import team.aliens.dms.kmp.feature.resetpassword.di.resetPasswordModule
import team.aliens.dms.kmp.feature.notification.di.notificationModule
import team.aliens.dms.kmp.feature.application.di.applicationModule
import team.aliens.dms.kmp.feature.editpassword.di.editPasswordModule
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
import team.aliens.dms.kmp.feature.remain.di.remainsModule

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
        notificationModule,
        settingModule,
        profileModule,
        editPasswordModule,
    )
}
