package team.aliens.dms.kmp.core.data.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.data.auth.repository.AuthRepository
import team.aliens.dms.kmp.core.data.auth.repository.AuthRepositoryImpl
import team.aliens.dms.kmp.core.data.file.repository.FileRepository
import team.aliens.dms.kmp.core.data.file.repository.FileRepositoryImpl
import team.aliens.dms.kmp.core.data.meal.repository.MealRepository
import team.aliens.dms.kmp.core.data.meal.repository.MealRepositoryImpl
import team.aliens.dms.kmp.core.data.notice.repository.NoticeRepository
import team.aliens.dms.kmp.core.data.notice.repository.NoticeRepositoryImpl
import team.aliens.dms.kmp.core.data.onboarding.OnboardingRepository
import team.aliens.dms.kmp.core.data.onboarding.OnboardingRepositoryImpl
import team.aliens.dms.kmp.core.data.points.repository.PointsRepository
import team.aliens.dms.kmp.core.data.points.repository.PointsRepositoryImpl
import team.aliens.dms.kmp.core.data.remains.repository.RemainsRepository
import team.aliens.dms.kmp.core.data.remains.repository.RemainsRepositoryImpl
import team.aliens.dms.kmp.core.data.schools.repository.SchoolsRepository
import team.aliens.dms.kmp.core.data.schools.repository.SchoolsRepositoryImpl
import team.aliens.dms.kmp.core.data.student.repository.StudentRepository
import team.aliens.dms.kmp.core.data.student.repository.StudentRepositoryImpl
import team.aliens.dms.kmp.core.data.user.repository.UserRepository
import team.aliens.dms.kmp.core.data.user.repository.UserRepositoryImpl
import team.aliens.dms.kmp.core.data.image.repository.ImageRepository
import team.aliens.dms.kmp.core.data.image.repository.ImageRepositoryImpl
import team.aliens.dms.kmp.core.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.kmp.core.data.latestudy.repository.LateStudyRepositoryImpl
import team.aliens.dms.kmp.core.data.notification.repository.NotificationRepository
import team.aliens.dms.kmp.core.data.notification.repository.NotificationRepositoryImpl
import team.aliens.dms.kmp.core.data.votes.repository.VotesRepository
import team.aliens.dms.kmp.core.data.votes.repository.VotesRepositoryImpl

val repositoryModule = module {
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::FileRepositoryImpl) { bind<FileRepository>() }
    singleOf(::MealRepositoryImpl) { bind<MealRepository>() }
    singleOf(::NoticeRepositoryImpl) { bind<NoticeRepository>() }
    singleOf(::StudentRepositoryImpl) { bind<StudentRepository>() }
    singleOf(::RemainsRepositoryImpl) { bind<RemainsRepository>() }
    singleOf(::VotesRepositoryImpl) { bind<VotesRepository>() }
    singleOf(::PointsRepositoryImpl) { bind<PointsRepository>() }
    singleOf(::SchoolsRepositoryImpl) { bind<SchoolsRepository>() }
    singleOf(::OnboardingRepositoryImpl) { bind<OnboardingRepository>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::ImageRepositoryImpl) { bind<ImageRepository>() }
    singleOf(::LateStudyRepositoryImpl) { bind<LateStudyRepository>() }
    singleOf(::NotificationRepositoryImpl) { bind<NotificationRepository>() }
}
