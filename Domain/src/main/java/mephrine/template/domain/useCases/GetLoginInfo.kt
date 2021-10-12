package mephrine.template.domain.useCases

import mephrine.template.domain.entities.LoginUser
import mephrine.template.domain.repositories.LoginRepository
import mephrine.template.domain.utilities.UseCase

class GetLoginInfo(private val loginRepository: LoginRepository) : UseCase<LoginUser, GetLoginInfo.Params>() {
  override suspend fun execute(params: Params) = loginRepository.login(params.account, params.password)

  data class Params(val account: String, val password: String)
}