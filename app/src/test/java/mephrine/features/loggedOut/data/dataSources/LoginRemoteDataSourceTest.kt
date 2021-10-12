package mephrine.features.loggedOut.data.dataSources

import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import mephrine.core.utils.UnitTest
import mephrine.fixture.fixture
import mephrine.fixture.loginDTOFixture
import mephrine.fixture.makeTestLoginModel
import mephrine.template.features.loggedOut.data.dataSources.LoginRemoteDataSource
import mephrine.template.features.loggedOut.data.dataSources.LoginRemoteDataSourceImpl
import mephrine.template.features.loggedOut.data.dataSources.services.LoginService
import mephrine.template.features.loggedOut.data.models.LoginModel
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.*
import kotlin.time.ExperimentalTime

class LoginRemoteDataSourceTest: UnitTest() {
  val service = mockk<LoginService>()

  lateinit var dataSource: LoginRemoteDataSource

  val testDispatcher = TestCoroutineDispatcher()
  private val loginDTO = LoginModel.makeTestLoginModel()


  @BeforeEach
  fun setUp() {
    dataSource = LoginRemoteDataSourceImpl(service)

    coEvery {
      service.login(any(), any()).resultData
    } returns loginDTOFixture.fixture()
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Nested
  @DisplayName("Status Code is 200")
  inner class SuccessCase {
    @BeforeEach
    fun arrange() {
      coEvery {
        service.login(any(), any()).statusCode
      } returns "200"
    }

    @ExperimentalTime
    @Test
    @DisplayName("should return a model when request is success")
    fun resultDataFailure() = testDispatcher.runBlockingTest {
      // given

      // when
      val result = dataSource.login("", "")

      // then
      result.test {
        expectItem() shouldEqual loginDTO
        expectComplete()
      }

    }
  }

  @Nested
  @DisplayName("Status Code is 401")
  inner class LoginErrorCase {

    @BeforeEach
    fun arrange() {
      coEvery {
        service.login(any(), any()).statusCode
      } returns "401"
    }

    @ExperimentalTime
    @Test
    @DisplayName("should return a error when status code is 401")
    fun resultDataFailure() = testDispatcher.runBlockingTest {
      // when
      val result = dataSource.login("", "")

      // then
      assertThrows<Throwable> { result.firstOrNull() }
    }
  }

  @Nested
  @DisplayName("Status Code others")
  inner class OthersErrorCase {

    @BeforeEach
    fun arrange() {
      coEvery {
        service.login(any(), any()).statusCode
      } returns "500"
    }

    @ExperimentalTime
    @Test
    @DisplayName("should return a error when status code is 500")
    fun resultDataFailure() = testDispatcher.runBlockingTest {
      // when
      val result = dataSource.login("", "")

      // then
      assertThrows<Throwable> { result.firstOrNull() }
    }
  }

}