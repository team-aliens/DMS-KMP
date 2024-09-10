package team.aliens.dms.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform