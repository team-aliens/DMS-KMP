package team.aliens.dms.kmp.core.network.latestudy.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import team.aliens.dms.kmp.core.network.latestudy.model.request.SubmitLateStudyRequest
import team.aliens.dms.kmp.core.network.latestudy.model.response.FetchStudyTypesResponse
import team.aliens.dms.kmp.core.network.latestudy.model.response.FetchTeachersResponse
import team.aliens.dms.kmp.core.network.latestudy.model.response.StudyApplicationStatusResponse

class KtorLateStudyDataSource(
    private val httpClient: HttpClient,
) : NetworkLateStudyDataSource {

    override suspend fun fetchStudyTypes(): FetchStudyTypesResponse =
        httpClient.get("/daybreaks/study-type").body()

    override suspend fun fetchTeachers(): FetchTeachersResponse =
        httpClient.get("/teachers/general").body()

    override suspend fun fetchMyStudyApplicationStatus(): StudyApplicationStatusResponse =
        httpClient.get("/daybreaks/study-application/my").body()

    override suspend fun submitLateStudy(request: SubmitLateStudyRequest) {
        httpClient.post("/daybreaks/study-application") {
            setBody(request)
        }
    }
}
