package mephrine.features.loggedOut.data.repositories

import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import mephrine.core.utils.UnitTest
import mephrine.fixture.fixture
import mephrine.fixture.loginDTOFixture
import mephrine.template.core.errors.LoginError
import mephrine.template.core.networks.NetworkInfo
import mephrine.template.features.loggedOut.data.dataSources.LoginLocalDataSource
import mephrine.template.features.loggedOut.data.dataSources.LoginRemoteDataSource
import mephrine.template.features.loggedOut.data.models.LoginModel
import mephrine.template.features.loggedOut.data.repositories.LoginRepositoryImpl
import mephrine.template.features.loggedOut.domain.entities.LoginUser
import mephrine.template.features.loggedOut.domain.repositories.LoginRepository
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeTrue
import org.junit.jupiter.api.*
import javax.security.auth.login.LoginException
import kotlin.time.ExperimentalTime

class LoginRepositoryImplTest : UnitTest() {
  private val networkInfo = mockk<NetworkInfo>()
  private val serviceDataSource = mockk<LoginRemoteDataSource>()
  private val localDataSource = mockk<LoginLocalDataSource>()

  private lateinit var repository: LoginRepository
  val testDispatcher = TestCoroutineDispatcher()

  val loginUser = LoginUser(
    email = "jh.kim@deepfine.ai",
    name = "김제현",
    profilePhoto = "/upload/user/2021/05/24/210511_173714.jpeg"
  )

  @BeforeEach
  fun setUp() {
    repository = LoginRepositoryImpl(networkInfo, serviceDataSource, localDataSource)
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }


  @Nested
  @DisplayName("API Service Test")
  inner class ServiceTest {
    @BeforeEach
    fun arrange() {
      coEvery {
        localDataSource.setPreferenceLoginData(any())
      } returns Unit
    }

    @Nested
    @DisplayName("Network is connected")
    inner class NetworkIsConnected {
      @BeforeEach
      fun arrange() {
        coEvery { networkInfo.isNetworkAvailable() }
          .returns(true)
      }

      @Test
      @DisplayName("should return a model when network is connected")
      fun resultDataSuccess() = runBlockingTest {
        // given
        val loginDTO = loginDTOFixture.fixture<LoginModel>()
        val loginDataChannel = ConflatedBroadcastChannel<LoginModel>()
        loginDataChannel.offer(loginDTO)

        coEvery {
          serviceDataSource.login(any(), any())
        } returns loginDataChannel.asFlow()


        // when
        val result = repository.login("", "").firstOrNull()

        // then
        Assertions.assertEquals(result, loginUser)
      }

      @ExperimentalTime
      @Test
      @DisplayName("should call local datasource when data flow onStart")
      fun saveLocalDataIsSucceed() = runBlockingTest {
        // given
        val loginDTO = loginDTOFixture.fixture<LoginModel>()
        val loginDataChannel = ConflatedBroadcastChannel<LoginModel>()
        loginDataChannel.offer(loginDTO)

        coEvery {
          serviceDataSource.login(any(), any())
        } returns loginDataChannel.asFlow()

        // when
        repository.login("", "").firstOrNull()

        coVerify { localDataSource.setPreferenceLoginData(any()) }
      }

      @ExperimentalTime
      @Test
      @DisplayName("should not running localDataSource flow when service occurs error")
      fun resultDataFailure() = testDispatcher.runBlockingTest {
        // given
        coEvery {
          serviceDataSource.login(any(), any())
        } returns flow { throw LoginException() }

        // when
        repository.login("", "").test {
          expectError().`should be instance of`(LoginError::class.java)
          expectComplete()
        }
      }
    }

    @Nested
    @DisplayName("Network is disconnected")
    inner class NetworkIsDisConnected {
      @BeforeEach
      fun arrange() {
        coEvery { networkInfo.isNetworkAvailable() }
          .returns(false)
      }

      @ExperimentalTime
      @Test
      @DisplayName("should be return a failure when network is disconnected")
      fun networkDisConnectedTest() = runBlockingTest {
        // when
        val result = repository.login("", "")

        // then
        result.test {
          expectError()
        }
      }
    }
  }

  @Nested
  @DisplayName("Login Local Test")
  inner class LocalTest {
    @BeforeEach
    fun arrange() {

    }

    @Nested
    @DisplayName("Get Preference Data")
    inner class PreferenceTest {
      @BeforeEach
      fun arrange() {
        coEvery { networkInfo.isNetworkAvailable() }
          .returns(true)
      }

      @ExperimentalTime
      @Test
      @DisplayName("should get strings(loginKey, corpId)")
      fun getPreferenceDataTest() = runBlockingTest {
        // Given
        val loginDataChannel = ConflatedBroadcastChannel<String>()
        loginDataChannel.offer("abcdefg")

        coEvery {
          localDataSource.getPreferenceLoginData()
        } returns loginDataChannel.asFlow()

        // When
        val result = repository.getLoginPref()

        // Then
        result.test {
          expectItem().apply {
            firstOrNull() shouldBe "abcdefg"
          }
          expectComplete()
        }
      }
    }
  }
}