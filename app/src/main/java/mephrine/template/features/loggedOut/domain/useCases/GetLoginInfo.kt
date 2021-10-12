package mephrine.template.features.loggedOut.domain.useCases

import kotlinx.coroutines.flow.Flow
import mephrine.template.core.usecases.UseCase
import mephrine.template.features.loggedOut.domain.entities.LoginUser
import mephrine.template.features.loggedOut.domain.repositories.LoginRepository

class GetLoginInfo(private val loginRepository: LoginRepository) : UseCase<LoginUser, GetLoginInfo.Params>() {
  override suspend fun execute(params: Params) = loginRepository.login(params.account, params.password)

  data class Params(val account: String, val password: String)
}