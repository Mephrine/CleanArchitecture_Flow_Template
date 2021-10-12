package mephrine.template.core.utils

import com.google.gson.GsonBuilder
import java.lang.reflect.Type

object GsonConverter {
  private val gson = GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

  fun toJson(anyObject: Any): String? = try {
    gson.toJson(anyObject)
  } catch (exception: Exception) {
    null
  }

  fun <T> fromJson(jsonString: String, type: Type): T? = try {
    gson.fromJson(jsonString, type)
  } catch (exception: Exception) {
    null
  }

  fun <T> fromJson(jsonString: String, classType: Class<T>): T? = try {
    gson.fromJson(jsonString, classType)
  } catch (exception: Exception) {
    null
  }

}