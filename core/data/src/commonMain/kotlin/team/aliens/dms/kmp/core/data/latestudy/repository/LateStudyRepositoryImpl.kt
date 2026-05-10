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

    override suspend fun fetchMyStudyApplicationStatus(): StudyApplicationStatusModel =
        dataSource.fetchMyStudyApplicationStatus().let {
            StudyApplicationStatusModel(
                status = it.status,
                startDate = it.startDate,
                endDate = it.endDate,
            )
        }

    override suspend fun submitLateStudy(request: SubmitLateStudyRequest) {
        dataSource.submitLateStudy(request)
    }
}
