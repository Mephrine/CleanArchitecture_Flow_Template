package mephrine.template.core.networks

import android.content.Context
import android.net.NetworkCapabilities
import mephrine.template.core.extensions.connectivityManager

interface NetworkInfo {
  fun isNetworkAvailable(): Boolean
}

class NetworkInfoImpl(private val context: Context) : NetworkInfo {
  override fun isNetworkAvailable(): Boolean {
    val connectivityManager = context.connectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork =
      connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
      activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
      activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
      activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
      activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
      else -> false
    }
  }
}
