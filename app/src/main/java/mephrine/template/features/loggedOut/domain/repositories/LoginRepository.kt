package mephrine.template.features.loggedOut.domain.repositories

import kotlinx.coroutines.flow.Flow
import mephrine.template.features.loggedOut.domain.entities.LoginUser

interface LoginRepository {
  suspend fun login(account : String, password : String) : Flow<LoginUser>
  suspend fun getLoginPref(): Flow<String>
}