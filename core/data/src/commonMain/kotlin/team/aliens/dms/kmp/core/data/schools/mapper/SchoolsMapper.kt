package team.aliens.dms.kmp.core.data.schools.mapper

import team.aliens.dms.kmp.core.model.schools.SchoolModel
import team.aliens.dms.kmp.core.network.schools.model.dto.SchoolDto

internal fun List<SchoolDto>.toModel() = map { it.toModel() }

private fun SchoolDto.toModel() = SchoolModel(
    id = id,
    name = name,
    address = address,
)
