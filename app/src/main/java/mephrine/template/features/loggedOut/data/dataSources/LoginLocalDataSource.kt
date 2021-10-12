package mephrine.template.features.loggedOut.data.dataSources

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mephrine.template.core.errors.CacheError
import mephrine.template.core.preference.Preference

interface LoginLocalDataSource {
  fun setPreferenceLoginData(account: String)
  suspend fun getPreferenceLoginData(): Flow<String>
}

class LoginLocalDataSourceImpl(
  private val preference: Preference
) : LoginLocalDataSource {
  override fun setPreferenceLoginData(account: String) {
    try {
      preference.loginID = account
    } catch (e: Exception) {
      throw CacheError()
    }
  }

  override suspend fun getPreferenceLoginData(): Flow<String> = flow {
    emit(preference.loginID)
  }
}