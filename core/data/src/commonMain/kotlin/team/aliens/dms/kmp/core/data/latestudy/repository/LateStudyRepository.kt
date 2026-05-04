package team.aliens.dms.kmp.core.data.latestudy.repository

import team.aliens.dms.kmp.core.model.latestudy.StudyApplicationStatusModel
import team.aliens.dms.kmp.core.model.latestudy.StudyTypeModel
import team.aliens.dms.kmp.core.model.latestudy.TeacherModel
import team.aliens.dms.kmp.core.network.latestudy.model.request.SubmitLateStudyRequest

interface LateStudyRepository {
    suspend fun fetchStudyTypes(): List<StudyTypeModel>

    suspend fun fetchTeachers(): List<TeacherModel>

    suspend fun fetchMyStudyApplicationStatus(): StudyApplicationStatusModel

    suspend fun submitLateStudy(request: SubmitLateStudyRequest)
}
