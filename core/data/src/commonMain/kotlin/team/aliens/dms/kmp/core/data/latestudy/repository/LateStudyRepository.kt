package team.aliens.dms.kmp.core.data.latestudy.repository

import team.aliens.dms.kmp.core.network.latestudy.model.request.SubmitLateStudyRequest
import team.aliens.dms.kmp.core.network.latestudy.model.response.FetchStudyTypesResponse
import team.aliens.dms.kmp.core.network.latestudy.model.response.FetchTeachersResponse
import team.aliens.dms.kmp.core.network.latestudy.model.response.StudyApplicationStatusResponse

interface LateStudyRepository {
    suspend fun fetchStudyTypes(): FetchStudyTypesResponse

    suspend fun fetchTeachers(): FetchTeachersResponse

    suspend fun fetchMyStudyApplicationStatus(): StudyApplicationStatusResponse

    suspend fun submitLateStudy(request: SubmitLateStudyRequest)
}
