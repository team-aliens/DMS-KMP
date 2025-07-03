package team.aliens.dms.kmp.feature.vote.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import team.aliens.dms.kmp.core.model.student.StudentModel
import team.aliens.dms.kmp.core.model.type.VoteType
import team.aliens.dms.kmp.core.model.votes.VoteItemModel

@Composable
internal fun VoteContent(
    modifier: Modifier = Modifier,
    voteType: VoteType,
    title: String,
    options: List<VoteItemModel>,
    students: List<StudentModel>,
    modelStudents: List<StudentModel>,
    selectItem: String,
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        when (voteType) {
            VoteType.OPTION_VOTE -> {
                OptionContent(
                    title = title,
                    options = options,
                    selectItem = selectItem,
                    onSelect = onSelect,
                )
            }

            VoteType.STUDENT_VOTE -> {
                StudentContent(
                    title = title,
                    students = students,
                    selectItem = selectItem,
                    onSelect = onSelect,
                )
            }

            VoteType.APPROVAL_VOTE -> {
                ApprovalContent(
                    title = title,
                    options = options,
                    selectItem = selectItem,
                    onSelect = onSelect,
                )
            }

            VoteType.MODEL_STUDENT_VOTE -> {
                StudentContent(
                    title = title,
                    students = modelStudents,
                    selectItem = selectItem,
                    onSelect = onSelect,
                )
            }
        }
    }
}
