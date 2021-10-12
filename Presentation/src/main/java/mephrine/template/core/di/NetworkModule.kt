package mephrine.template.core.di


import com.google.gson.GsonBuilder
import mephrine.template.BuildConfig
import mephrine.template.core.networks.UnsafeOKHttpClient
import mephrine.template.data.utilities.NetworkInfo
import mephrine.template.data.utilities.NetworkInfoImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

  single {
    GsonBuilder().create()
  }
  single<NetworkInfo> {
    NetworkInfoImpl(androidApplication())
  }
  single {
    UnsafeOKHttpClient.unsafeOkHttpClient
  }

  single<Retrofit> {
    Retrofit.Builder()
      .baseUrl(BuildConfig.API_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(get())
      .build()
  }
}