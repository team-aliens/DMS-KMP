package team.aliens.dms.kmp.core.model.latestudy

data class StudyApplicationStatusModel(
    val status: String,
    val dates: List<String> = emptyList(),
    val teacherName: String? = null,
    val typeName: String? = null,
    val reason: String? = null,
)
