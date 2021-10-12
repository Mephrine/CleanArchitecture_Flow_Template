package mephrine.template.core.di


import mephrine.template.data.repositories.LoginRepositoryImpl
import mephrine.template.domain.repositories.LoginRepository
import org.koin.dsl.module


val repositoryModule = module {
  single<LoginRepository> {
    LoginRepositoryImpl(get(), get(), get())
  }
}