package team.aliens.dms.kmp.core.network.student.model.request

import kotlinx.datetime.LocalDate

data class GetCandidateModelStudentsRequest(
    val query: Query,
) {
    data class Query(
        val requestDate: LocalDate,
    )
}
