package team.aliens.dms.kmp.feature.latestudy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.exception.UnknownException
import team.aliens.dms.kmp.core.common.exception.network.NetworkException
import team.aliens.dms.kmp.core.common.exception.network.BadRequestException
import team.aliens.dms.kmp.core.common.exception.network.ForbiddenException
import team.aliens.dms.kmp.core.common.exception.network.InternalServerErrorException
import team.aliens.dms.kmp.core.common.exception.network.NotFoundException
import team.aliens.dms.kmp.core.common.exception.network.UnAuthorizedException
import team.aliens.dms.kmp.core.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.kmp.core.model.latestudy.StudyApplicationStatusModel

class LateStudyDetailViewModel(
    private val lateStudyRepository: LateStudyRepository,
) : ViewModel() {
    var application by mutableStateOf<StudyApplicationStatusModel?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    var isCancelling by mutableStateOf(false)
        private set

    init {
        fetchApplication()
    }

    private fun fetchApplication() {
        viewModelScope.launch {
            isLoading = true
            try {
                application = lateStudyRepository.fetchMyStudyApplicationStatus()
            } catch (e: CancellationException) {
                throw e
            } catch (e: NetworkException) {
                logFetchFailure(e)
            } catch (e: UnknownException) {
                logFetchFailure(e)
            } finally {
                isLoading = false
            }
        }
    }

    fun cancelApplication(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    ) {
        if (application?.status != "PENDING" || isCancelling) return

        viewModelScope.launch {
            isCancelling = true
            try {
                lateStudyRepository.cancelMyStudyApplication()
                onSuccess()
            } catch (e: CancellationException) {
                throw e
            } catch (e: NetworkException) {
                onFailure(e.toCancelErrorMessage())
            } catch (e: UnknownException) {
                onFailure(e.toCancelErrorMessage())
            } finally {
                isCancelling = false
            }
        }
    }
}

private fun LateStudyDetailViewModel.logFetchFailure(throwable: Throwable) {
    Logger.a(throwable) { "새벽 자습 신청 내역 조회에 실패했습니다." }
}

private fun Throwable.toCancelErrorMessage(): String =
    when (this) {
        is BadRequestException -> "승인된 신청은 취소할 수 없습니다."
        is UnAuthorizedException -> "로그인이 만료되었습니다. 다시 로그인해주세요."
        is ForbiddenException -> "새벽 자습 신청을 취소할 권한이 없습니다."
        is NotFoundException -> "취소할 새벽 자습 신청이 없습니다."
        is InternalServerErrorException -> "서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요."
        else -> "새벽 자습 신청 취소에 실패했습니다."
    }
