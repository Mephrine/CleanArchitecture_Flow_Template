package mephrine.template.data.utilities

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mephrine.template.utility.errors.NetworkDisconnect
import mephrine.template.utility.errors.ServerError

object CatchFlow {
  suspend fun <T> networkCatch(
    errorMessage: String? = null,
    function: suspend () -> Flow<T>
  ) = try {
    function.invoke()
  } catch (exception: Exception) {
    print(exception)
    throw ServerError(errorMessage)
  }

  suspend fun <T> networkDisconnectCatch(
    networkInfo: NetworkInfo,
    function: suspend () -> Flow<T>
  ): Flow<T> = try {
    when (networkInfo.isNetworkAvailable()) {
      true -> function.invoke()
      false -> flow {
        throw NetworkDisconnect()
      }
    }
  } catch (exception: Exception) {
    print(exception)
    flow { throw ServerError(exception.message) }
  }
}