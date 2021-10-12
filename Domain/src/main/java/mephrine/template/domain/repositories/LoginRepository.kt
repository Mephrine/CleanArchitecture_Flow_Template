package mephrine.template.domain.repositories

import kotlinx.coroutines.flow.Flow
import mephrine.template.domain.entities.LoginUser

interface LoginRepository {
  suspend fun login(account : String, password : String) : Flow<LoginUser>
  suspend fun getLoginPref(): Flow<String>
}