package team.aliens.dms.kmp.feature.latestudy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.kmp.core.network.latestudy.model.request.SubmitLateStudyRequest
import team.aliens.dms.kmp.core.network.latestudy.model.response.TeacherResponse
import team.aliens.dms.kmp.core.network.latestudy.model.response.StudyTypeResponse

class LateStudyViewModel(
    private val lateStudyRepository: LateStudyRepository,
) : ViewModel() {

    var studyTypes by mutableStateOf<List<StudyTypeResponse>>(emptyList())
        private set

    var selectedTypeId by mutableStateOf<String?>(null)
        private set

    var teachers by mutableStateOf<List<TeacherResponse>>(emptyList())
        private set

    init {
        fetchStudyTypes()
        fetchTeachers()
    }

    private fun fetchStudyTypes() {
        viewModelScope.launch {
            runCatching {
                lateStudyRepository.fetchStudyTypes()
            }.onSuccess { response ->
                studyTypes = response.types
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    private fun fetchTeachers() {
        viewModelScope.launch {
            runCatching {
                lateStudyRepository.fetchTeachers()
            }.onSuccess { response ->
                teachers = response.teachers
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun selectStudyType(typeId: String) {
        selectedTypeId = typeId
    }

    fun submitLateStudy(
        teacherId: String,
        typeId: String,
        reason: String,
        startDate: String,
        endDate: String,
    ) {
        viewModelScope.launch {
            runCatching {
                lateStudyRepository.submitLateStudy(
                    SubmitLateStudyRequest(
                        teacherId = teacherId,
                        typeId = typeId,
                        reason = reason,
                        startDate = startDate,
                        endDate = endDate,
                    ),
                )
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}
