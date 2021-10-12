package mephrine.features.loggedOut.presentation.viewModels

import io.mockk.clearAllMocks
import io.mockk.mockk
import mephrine.core.utils.UnitTest
import mephrine.template.features.loggedOut.domain.useCases.GetLoginInfo
import mephrine.template.features.loggedOut.presentation.viewModels.LoggedOutViewModel
import org.junit.jupiter.api.*

class LoggedOutViewModelTest : UnitTest() {
  lateinit var viewModel: LoggedOutViewModel
  val requestLoginInfo = mockk<GetLoginInfo>()

  @BeforeEach
  fun setUp() {
    viewModel = LoggedOutViewModel(requestLoginInfo)
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Nested
  @DisplayName("Login Validation Test")
  inner class LoginValidationLoginTest {
    @BeforeEach
    fun arrange() {

    }

    @Test
    fun test() {

    }
  }
}