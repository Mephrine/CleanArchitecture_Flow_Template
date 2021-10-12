package mephrine.template.core.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import java.util.*

object LocaleUtils {
  @JvmStatic
  fun setLanguage(context: Context, languageCode: LanguageCode) {
    val configuration = Configuration()
    configuration.setLocale(
      getLanguageCodeToLocale(
        languageCode
      )
    )
    context.createConfigurationContext(configuration)
  }

  val deviceLocale: String
    get() {
      try {
        val locale =
          Resources.getSystem().configuration.locales[0]
        return locale.isO3Country
      } catch (e: MissingResourceException) {
        Log.e("LocaleUtils", e.toString())
      }
      return ""
    }

  private fun getLanguageCodeToLocale(languageCode: LanguageCode): Locale {
    return when (languageCode) {
      LanguageCode.KOREAN -> Locale.KOREA
      else -> Locale.ENGLISH
    }
  }

  enum class LanguageCode {
    KOREAN, ENGLISH
  }
}