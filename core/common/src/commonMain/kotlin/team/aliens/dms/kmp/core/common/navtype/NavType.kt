package team.aliens.dms.kmp.core.common.navtype

import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.core.model.type.VoteType
import team.aliens.dms.kmp.core.model.votes.VoteModel
import kotlin.reflect.typeOf

val VoteTypeNavType = typeOf<VoteType>() to serializableType<VoteType>()

val SignUpDataNavType = typeOf<SignUpData>() to serializableType<SignUpData>()
val VoteNavType = typeOf<VoteModel>() to serializableType<VoteModel>()
