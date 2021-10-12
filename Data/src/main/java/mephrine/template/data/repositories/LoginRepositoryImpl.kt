package mephrine.template.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import mephrine.template.data.dataSources.LoginLocalDataSource
import mephrine.template.data.dataSources.LoginRemoteDataSource
import mephrine.template.data.utilities.CatchFlow
import mephrine.template.data.utilities.NetworkInfo
import mephrine.template.utility.errors.ParseError
import mephrine.template.domain.entities.LoginUser
import mephrine.template.domain.repositories.LoginRepository

class LoginRepositoryImpl(
  private val networkInfo: NetworkInfo,
  private val remoteDataSource: LoginRemoteDataSource,
  private val localDataSource: LoginLocalDataSource
) :LoginRepository {
  override suspend fun login(account : String, password : String): Flow<LoginUser> =
    CatchFlow.networkDisconnectCatch(networkInfo) {
      remoteDataSource.login(account, password)
    }.onStart {
      localDataSource.setPreferenceLoginData(account)
    }.mapLatest { response ->
      response?.let {
        it.toLoginUser()
      } ?: throw ParseError()
    }

  override suspend fun getLoginPref(): Flow<String> =
    localDataSource.getPreferenceLoginData()

}