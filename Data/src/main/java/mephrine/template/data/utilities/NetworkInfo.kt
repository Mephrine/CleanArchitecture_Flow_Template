package mephrine.template.data.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

interface NetworkInfo {
  fun isNetworkAvailable(): Boolean
}

class NetworkInfoImpl(private val context: Context) : NetworkInfo {
  @SuppressLint("MissingPermission")
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

private val Context.connectivityManager: ConnectivityManager
  get() =
    this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
