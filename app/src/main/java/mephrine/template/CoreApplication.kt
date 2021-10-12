package mephrine.template

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import mephrine.template.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CoreApplication : MultiDexApplication() {
  override fun onCreate() {
    super.onCreate()

    // DI 세팅
    startKoin {
      androidLogger(Level.DEBUG)
      androidContext(this@CoreApplication)
      modules(
        listOf(
          apiServiceModule,
          dataSourceModule,
          localModule,
          networkModule,
          repositoryModule,
          useCaseModule,
          viewModelModule
        )
      )
    }
  }

  override fun attachBaseContext(base: Context?) {
    super.attachBaseContext(base)
    MultiDex.install(this)
  }
}