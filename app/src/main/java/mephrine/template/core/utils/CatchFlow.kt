package mephrine.template.core.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mephrine.template.core.errors.NetworkDisconnect
import mephrine.template.core.errors.ServerError
import mephrine.template.core.networks.NetworkInfo

object CatchFlow {
  suspend fun <T> networkCatch(
    errorMessage: String? = null,
    function: suspend () -> Flow<T>
  ) = try {
    function.invoke()
  } catch (exception: Exception) {
    L.e(exception)
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
    L.e(exception)
    flow { throw ServerError(exception.message) }
  }
}