package team.aliens.dms.kmp.core.data.latestudy.repository

import team.aliens.dms.kmp.core.model.latestudy.StudyApplicationStatusModel
import team.aliens.dms.kmp.core.model.latestudy.StudyTypeModel
import team.aliens.dms.kmp.core.model.latestudy.TeacherModel
import team.aliens.dms.kmp.core.network.latestudy.datasource.NetworkLateStudyDataSource
import team.aliens.dms.kmp.core.network.latestudy.model.request.SubmitLateStudyRequest

class LateStudyRepositoryImpl(
    private val dataSource: NetworkLateStudyDataSource,
) : LateStudyRepository {
    override suspend fun fetchStudyTypes(): List<StudyTypeModel> =
        dataSource.fetchStudyTypes().types.map {
            StudyTypeModel(
                id = it.id,
                name = it.name,
            )
        }

    override suspend fun fetchTeachers(): List<TeacherModel> =
        dataSource.fetchTeachers().teachers.map {
            TeacherModel(
                id = it.id,
                name = it.name,
            )
        }

    override suspend fun fetchMyStudyApplicationStatus(): StudyApplicationStatusModel? =
        dataSource.fetchMyStudyApplicationStatus()?.let {
            StudyApplicationStatusModel(
                status = it.status,
                dates = it.dates,
                teacherName = it.teacherName,
                typeName = it.typeName,
                reason = it.reason,
            )
        }

    override suspend fun submitLateStudy(request: SubmitLateStudyRequest) {
        dataSource.submitLateStudy(request)
    }

    override suspend fun cancelMyStudyApplication() {
        dataSource.cancelMyStudyApplication()
    }
}
