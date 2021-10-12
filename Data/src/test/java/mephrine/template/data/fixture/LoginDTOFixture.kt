package mephrine.template.data.fixture

import mephrine.template.data.models.LoginModel
import mephrine.template.domain.entities.LoginUser

val loginDTOFixture = """
  {
      "email": "mephrine@aaaa.com",
      "name": "Mephrine",
      "profile_photo": "https://abcdefg.com",
    }
""".trimIndent()


fun LoginModel.Companion.makeTestLoginModel()
  = LoginModel(email = "mephrine@aaaa.com", name = "Mephrine", profile = "https://abcdefg.com")


fun LoginUser.Companion.makeTestLoginUser()
  = LoginUser(email = "mephrine@aaaa.com", name = "Mephrine", profilePhoto = "https://abcdefg.com")