package team.aliens.dms.kmp.core.data.latestudy.repository

import team.aliens.dms.kmp.core.network.latestudy.datasource.NetworkLateStudyDataSource
import team.aliens.dms.kmp.core.network.latestudy.model.request.SubmitLateStudyRequest
import team.aliens.dms.kmp.core.network.latestudy.model.response.FetchStudyTypesResponse
import team.aliens.dms.kmp.core.network.latestudy.model.response.FetchTeachersResponse
import team.aliens.dms.kmp.core.network.latestudy.model.response.StudyApplicationStatusResponse

class LateStudyRepositoryImpl(
    private val dataSource: NetworkLateStudyDataSource,
) : LateStudyRepository {

    override suspend fun fetchStudyTypes(): FetchStudyTypesResponse =
        dataSource.fetchStudyTypes()

    override suspend fun fetchTeachers(): FetchTeachersResponse =
        dataSource.fetchTeachers()

    override suspend fun fetchMyStudyApplicationStatus(): StudyApplicationStatusResponse =
        dataSource.fetchMyStudyApplicationStatus()

    override suspend fun submitLateStudy(request: SubmitLateStudyRequest) {
        dataSource.submitLateStudy(request)
    }
}
