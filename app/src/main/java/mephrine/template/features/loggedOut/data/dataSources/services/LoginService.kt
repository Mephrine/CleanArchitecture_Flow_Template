package mephrine.template.features.loggedOut.data.dataSources.services

import mephrine.template.core.models.ResultDTO
import mephrine.template.features.loggedOut.data.models.LoginModel
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {
  @GET("/api/v1/login/mobile_login")
  suspend fun login(
    @Query("login_id") account: String,
    @Query("login_pw") password: String,
  ) : ResultDTO<LoginModel?>
}