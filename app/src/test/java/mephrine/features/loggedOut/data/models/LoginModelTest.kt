package mephrine.features.loggedOut.data.models

import mephrine.fixture.fixture
import mephrine.fixture.loginDTOFixture
import mephrine.fixture.makeTestLoginUser
import mephrine.template.features.loggedOut.data.models.LoginModel
import mephrine.template.features.loggedOut.domain.entities.LoginUser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class LoginModelTest {
  val loginUser = LoginUser.makeTestLoginUser()

  @Nested
  inner class ToJSON {
    @Test
    @DisplayName("should be returned LoginUser class when I call 'toJSON' function")
    fun changeToLoginUser() {
      //assign
      val result = loginDTOFixture.fixture<LoginModel>().toLoginUser()
      Assertions.assertEquals(result, loginUser)
    }
  }
}