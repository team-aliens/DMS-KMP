package team.aliens.dms.kmp.feature.latestudy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.exception.network.ConflictException
import team.aliens.dms.kmp.core.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.kmp.core.model.latestudy.StudyTypeModel
import team.aliens.dms.kmp.core.model.latestudy.TeacherModel
import team.aliens.dms.kmp.core.network.latestudy.model.request.SubmitLateStudyRequest

class LateStudyViewModel(
    private val lateStudyRepository: LateStudyRepository,
) : ViewModel() {

    var studyTypes by mutableStateOf<List<StudyTypeModel>>(emptyList())
        private set

    var selectedTypeId by mutableStateOf<String?>(null)
        private set

    var teachers by mutableStateOf<List<TeacherModel>>(emptyList())
        private set

    var teacherKeyword by mutableStateOf("")
        private set

    var selectedTeacherId by mutableStateOf<String?>(null)
        private set

    var reason by mutableStateOf("")
        private set

    var isSubmitting by mutableStateOf(false)
        private set

    init {
        fetchStudyTypes()
        fetchTeachers()
    }

    private fun fetchStudyTypes() {
        viewModelScope.launch {
            runCatching {
                lateStudyRepository.fetchStudyTypes()
            }.onSuccess {
                studyTypes = it
            }
        }
    }

    private fun fetchTeachers() {
        viewModelScope.launch {
            runCatching {
                lateStudyRepository.fetchTeachers()
            }.onSuccess {
                teachers = it
            }
        }
    }

    fun selectStudyType(typeId: String) {
        selectedTypeId = typeId
    }

    fun updateTeacherKeyword(keyword: String) {
        teacherKeyword = keyword
        selectedTeacherId = teachers
            .firstOrNull { it.name == keyword }
            ?.id
    }

    fun selectTeacher(teacher: TeacherModel) {
        teacherKeyword = teacher.name
        selectedTeacherId = teacher.id
    }

    fun updateReason(reason: String) {
        this.reason = reason
    }

    fun submitLateStudy(
        startDate: String,
        endDate: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        val teacherId = selectedTeacherId
        val typeId = selectedTypeId

        if (teacherId == null || typeId == null || reason.isBlank()) {
            onFailure("모두 선택해주세요")
            return
        }

        viewModelScope.launch {
            isSubmitting = true

            try {
                lateStudyRepository.submitLateStudy(
                    SubmitLateStudyRequest(
                        teacherId = teacherId,
                        typeId = typeId,
                        reason = reason,
                        startDate = startDate,
                        endDate = endDate,
                    ),
                )
                onSuccess()
            } catch (e: ConflictException) {
                onFailure("이미 새벽 자습을 신청했습니다.")
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                onFailure("새벽 자습 신청에 실패했습니다.")
            } finally {
                isSubmitting = false
            }
        }
    }
}
