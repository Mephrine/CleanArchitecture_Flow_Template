package mephrine.template.core.di


import mephrine.template.features.loggedOut.data.repositories.LoginRepositoryImpl
import mephrine.template.features.loggedOut.domain.repositories.LoginRepository
import org.koin.dsl.module


val repositoryModule = module {
  single<LoginRepository> {
    LoginRepositoryImpl(get(), get(), get())
  }
}