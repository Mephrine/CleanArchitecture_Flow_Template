package mephrine.template.core.di

import mephrine.template.data.dataSources.LoginLocalDataSource
import mephrine.template.data.dataSources.LoginLocalDataSourceImpl
import mephrine.template.data.dataSources.LoginRemoteDataSource
import mephrine.template.data.dataSources.LoginRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
  single<LoginRemoteDataSource> {
    LoginRemoteDataSourceImpl(get())
  }

  single<LoginLocalDataSource> {
    LoginLocalDataSourceImpl(get())
  }
}