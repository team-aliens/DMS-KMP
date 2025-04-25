package team.aliens.dms.kmp.core.common.navtype

import team.aliens.dms.kmp.core.model.signup.SignUpData
import kotlin.reflect.typeOf

val SignUpDataNavType = typeOf<SignUpData>() to serializableType<SignUpData>()
