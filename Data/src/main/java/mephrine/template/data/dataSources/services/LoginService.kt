package mephrine.template.data.dataSources.services

import mephrine.template.data.models.LoginModel
import mephrine.template.data.utilities.ResultDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {
  @GET("/api/v1/login/mobile_login")
  suspend fun login(
    @Query("login_id") account: String,
    @Query("login_pw") password: String,
  ) : ResultDTO<LoginModel?>
}