package mephrine.template.data.models

import mephrine.template.data.fixture.fixture
import mephrine.template.data.fixture.loginDTOFixture
import mephrine.template.data.fixture.makeTestLoginUser
import mephrine.template.domain.entities.LoginUser
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