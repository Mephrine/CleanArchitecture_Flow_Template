package mephrine.template.core.di

import mephrine.template.features.loggedOut.data.dataSources.services.LoginService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiServiceModule = module {
  single(createdAtStart = false) {
    get<Retrofit>()
      .create(LoginService::class.java)
  }
}