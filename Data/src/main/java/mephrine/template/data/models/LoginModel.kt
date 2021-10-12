package mephrine.template.data.models

import com.google.gson.annotations.SerializedName
import mephrine.template.domain.entities.LoginUser

data class LoginModel(
  val email: String?,
  val name: String,
  @SerializedName("profile_photo")
  val profile: String?,
) {
  fun toLoginUser(): LoginUser {
    return LoginUser(
      email = email ?: "",
      name = name,
      profilePhoto = profile ?: "",
    )
  }

  companion object {
    val empty = LoginModel(email = "", name = "", profile = "")
  }
}
