package team.aliens.dms.kmp.network

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform