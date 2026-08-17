package team.aliens.dms.kmp.feature.latestudy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import team.aliens.dms.kmp.core.common.exception.network.BadRequestException
import team.aliens.dms.kmp.core.common.exception.network.ConflictException
import team.aliens.dms.kmp.core.common.exception.network.ForbiddenException
import team.aliens.dms.kmp.core.common.exception.network.InternalServerErrorException
import team.aliens.dms.kmp.core.common.exception.network.NotFoundException
import team.aliens.dms.kmp.core.common.exception.network.TooManyRequestsException
import team.aliens.dms.kmp.core.common.exception.network.UnAuthorizedException
import team.aliens.dms.kmp.core.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.kmp.core.model.latestudy.StudyTypeModel
import team.aliens.dms.kmp.core.model.latestudy.TeacherModel
import team.aliens.dms.kmp.core.network.latestudy.model.request.SubmitLateStudyRequest
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
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
        selectedTeacherId =
            teachers
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

        val selectedStartDate = startDate.toLocalDateOrNull()
        val selectedEndDate = endDate.toLocalDateOrNull()
        if (selectedStartDate == null || selectedEndDate == null) {
            onFailure("신청 정보를 다시 확인해주세요.")
            return
        }

        val validationMessage =
            validateLateStudyPeriod(
                startDate = selectedStartDate,
                endDate = selectedEndDate,
            )
        if (validationMessage != null) {
            onFailure(validationMessage)
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
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                onFailure(e.toLateStudyErrorMessage())
            } finally {
                isSubmitting = false
            }
        }
    }
}

private fun Exception.toLateStudyErrorMessage(): String =
    when (this) {
        is BadRequestException ->
            when (errorCode) {
                "DAYBREAK-400-1" -> "시작 날짜는 종료 날짜보다 늦을 수 없습니다."
                "DAYBREAK-400-2" -> "과거 날짜는 신청할 수 없습니다."
                "DAYBREAK-400-3" -> "새벽 자습은 월요일부터 목요일까지만 신청할 수 있습니다."
                else -> "신청 정보를 다시 확인해주세요."
            }

        is UnAuthorizedException -> "로그인이 만료되었습니다. 다시 로그인해주세요."
        is ForbiddenException -> "새벽 자습 신청 권한이 없습니다."
        is NotFoundException -> "담당 선생님 또는 새벽 자습 유형을 찾을 수 없습니다."
        is ConflictException -> "이미 새벽 자습을 신청했거나 승인된 신청이 있습니다."
        is TooManyRequestsException -> "요청이 너무 많습니다. 잠시 후 다시 시도해주세요."
        is InternalServerErrorException -> "서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요."
        else -> "새벽 자습 신청에 실패했습니다."
    }

@OptIn(ExperimentalTime::class)
private fun validateLateStudyPeriod(
    startDate: LocalDate,
    endDate: LocalDate,
): String? {
    if (startDate > endDate) return "시작 날짜는 종료 날짜보다 늦을 수 없습니다."

    val today =
        Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
    if (startDate < today || endDate < today) return "과거 날짜는 신청할 수 없습니다."

    var date = startDate
    while (date <= endDate) {
        if (date.dayOfWeek !in LATE_STUDY_ALLOWED_DAYS) {
            return "새벽 자습은 월요일부터 목요일까지만 신청할 수 있습니다."
        }
        date = date.plus(1, DateTimeUnit.DAY)
    }

    return null
}

private fun String.toLocalDateOrNull(): LocalDate? =
    runCatching {
        LocalDate.parse(this)
    }.getOrNull()

private val LATE_STUDY_ALLOWED_DAYS =
    setOf(
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
    )
