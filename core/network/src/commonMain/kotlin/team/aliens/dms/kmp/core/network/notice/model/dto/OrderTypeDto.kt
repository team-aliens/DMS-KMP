package team.aliens.dms.kmp.core.network.notice.model.dto

import kotlinx.serialization.Serializable

@Serializable
enum class OrderTypeDto {
    NEW,
    OLD,
}
