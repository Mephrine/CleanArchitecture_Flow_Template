package mephrine.template.domain.useCases

import io.mockk.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import mephrine.template.domain.entities.LoginUser
import mephrine.template.domain.repositories.LoginRepository
import mephrine.template.utility.errors.ServerError
import org.junit.jupiter.api.*
import kotlin.test.assertFailsWith

class GetLoginInfoTest {
  private val loginRepository = mockk<LoginRepository>()
  private lateinit var usecase: GetLoginInfo

  val loginUser = LoginUser(
    email = "mephrine@aaaa.com"
    ,name = "Mephrine"
    ,profilePhoto = "https://abcdefg.com")

  @BeforeEach
  fun setUp() {
    usecase = GetLoginInfo(loginRepository)
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Nested
  @DisplayName("LoginDataValid")
  inner class LoginDataValid {
    @BeforeEach
    fun arrange() {
      // arrange
      val channelLoginUser = ConflatedBroadcastChannel<LoginUser>()
      channelLoginUser.offer(loginUser)

      coEvery {
        loginRepository.login(any(), any())
      } returns channelLoginUser.asFlow()
    }

    @Test
    @DisplayName("should get login data when is successful")
    fun verifyLoginData() = runBlockingTest {
      // act
      val actualValue = usecase.execute(GetLoginInfo.Params("", "")).first()

      // assert
      coVerify { loginRepository.login("", "") }
      confirmVerified(loginRepository)
      Assertions.assertEquals(loginUser, actualValue)
    }
  }

  @Nested
  @DisplayName("LoginDataInValid")
  inner class LoginDataInValid {
    @BeforeEach
    fun arrange() {
      // arragne
      coEvery {
        loginRepository.login(any(), any())
      } coAnswers { throw ServerError("Server Failure") }
    }

    @Nested
    @DisplayName("should returns Throwable when is failure")
    inner class verifyLoginData {
      @Test
      @DisplayName("verifyLoginData")
      fun verifyLoginData() = runBlockingTest {
        // act
        // assert
        assertFailsWith <ServerError>(
          message = "Throws ServerError",
          block = {
            usecase.execute(GetLoginInfo.Params("", ""))
          }
        )
      }
    }
  }

///////////    Mockito
//    @ExperimentalCoroutinesApi
//    @Test
//    fun `should get login data when is successful`() = runBlockingTest {
//        // arrange
//        val channelLoginUser = ConflatedBroadcastChannel<LoginUser>()
//        channelLoginUser.offer(loginUser)
//
//        whenever(loginRepository.login(any()))
//            .thenReturn(channelLoginUser.asFlow())
//
//        // act
//        val actualValue = usecase.execute(GetLoginUser.Params("")).first()
//
//        // assert
//        verify(loginRepository, times(1)).login("")
//        Assert.assertEquals(loginUser, actualValue)
//    }
//
//    @Test
//    fun `should returns Throwable when is failure`() = runBlockingTest {
//        // arragne
//        whenever(loginRepository.login(any()))
//            .thenThrow(ServerError("Server Failure"))
//
//        // act
//        val call = usecase.execute(GetLoginUser.Params(""))
//
//        // assert
//    }

}