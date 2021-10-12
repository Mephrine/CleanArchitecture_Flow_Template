package mephrine.template.core.di

import mephrine.template.features.loggedOut.data.dataSources.LoginLocalDataSource
import mephrine.template.features.loggedOut.data.dataSources.LoginLocalDataSourceImpl
import mephrine.template.features.loggedOut.data.dataSources.LoginRemoteDataSource
import mephrine.template.features.loggedOut.data.dataSources.LoginRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
  single<LoginRemoteDataSource> {
    LoginRemoteDataSourceImpl(get())
  }

  single<LoginLocalDataSource> {
    LoginLocalDataSourceImpl(get())
  }
}