package mephrine.template.features.loggedOut.data.repositories

import kotlinx.coroutines.flow.*
import mephrine.template.core.errors.ParseError
import mephrine.template.core.networks.NetworkInfo
import mephrine.template.core.utils.CatchFlow
import mephrine.template.features.loggedOut.data.dataSources.LoginLocalDataSource
import mephrine.template.features.loggedOut.data.dataSources.LoginRemoteDataSource
import mephrine.template.features.loggedOut.domain.entities.LoginUser
import mephrine.template.features.loggedOut.domain.repositories.LoginRepository

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