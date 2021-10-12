package mephrine.template.features.loggedOut.data.dataSources

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mephrine.template.core.errors.LoginError
import mephrine.template.core.errors.ServerError
import mephrine.template.core.utils.CatchFlow
import mephrine.template.features.loggedOut.data.dataSources.services.LoginService
import mephrine.template.features.loggedOut.data.models.LoginModel

interface LoginRemoteDataSource {
  suspend fun login(account : String, password : String): Flow<LoginModel?>
}

class LoginRemoteDataSourceImpl(
  private val service: LoginService
) : LoginRemoteDataSource {
  override suspend fun login(
    account: String,
    password: String)
  : Flow<LoginModel?> = CatchFlow.networkCatch {
    testUser()
  }

  private suspend fun getLoginService(
    account: String,
    password: String
  ) = service.login(account, password)
    .responseToFlow(
      validation = { response ->
        response.statusCode == "200" && null != response.resultData
      },
      throwableError = { response ->
        if (response.errorCode == "401" || response.statusCode == "401") {
          throw LoginError()
        } else {
          throw ServerError(response.errorMessage)
        }
      })

  private suspend fun testUser() = flow {
    emit(LoginModel.empty)
  }
}