package team.aliens.dms.kmp.core.data.user.model

import team.aliens.dms.kmp.core.network.user.model.EditPasswordRequest

internal fun String.toRequest(newPassword: String): EditPasswordRequest =
    EditPasswordRequest(
        body =
            EditPasswordRequest.Body(
                currentPassword = this,
                newPassword = newPassword,
            ),
    )
