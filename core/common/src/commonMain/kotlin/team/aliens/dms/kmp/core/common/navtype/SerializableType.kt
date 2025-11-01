package team.aliens.dms.kmp.core.common.navtype

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T : Any?> serializableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {

    override fun get(bundle: SavedState, key: String): T? {
        return bundle.read {
            this.getString(key).let<String,T>(json::decodeFromString)
        }
    }

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = Json.encodeToString(value)

    override fun put(bundle: SavedState, key: String, value: T) {
        return bundle.write {
            this.putString(key, json.encodeToString(value))
        }
    }
}
