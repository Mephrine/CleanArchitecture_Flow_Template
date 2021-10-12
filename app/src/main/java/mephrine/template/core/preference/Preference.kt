package mephrine.template.core.preference

import android.content.Context

class Preference(private val context: Context) {
  companion object {
    const val PREFERENCE_NAME = "templateSharedPreferences"
    const val LOGIN_ID = ""
  }

  var loginID: String
    set(value) = put(LOGIN_ID, value)
    get() = getString(LOGIN_ID) ?: ""

  // Shared Preferences 파일명
  private val sharedPreferences =
    context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

  private fun remove(key: String) {
    sharedPreferences.edit().remove(key).apply()
  }

  private fun clearAll() {
    sharedPreferences.edit().clear().apply()
  }

  private fun <T> put(key: String, value: T) {
    when(value) {
      is String -> sharedPreferences.edit().putString(key, value).apply()
      is Boolean -> sharedPreferences.edit().putBoolean(key, value).apply()
      is Int -> sharedPreferences.edit().putInt(key, value).apply()
      is Long -> sharedPreferences.edit().putLong(key, value).apply()
      is Float -> sharedPreferences.edit().putFloat(key, value).apply()
    }
  }

  private fun getString(key: String) = sharedPreferences.getString(key, "")
  private fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)
  private fun getInt(key: String) = sharedPreferences.getInt(key, 0)
  private fun getLong(key: String) = sharedPreferences.getLong(key, 0L)
  private fun getFloat(key: String) = sharedPreferences.getFloat(key, 0F)
}