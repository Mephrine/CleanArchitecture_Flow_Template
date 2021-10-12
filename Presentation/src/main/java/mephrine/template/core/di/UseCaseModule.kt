package mephrine.template.core.di

import mephrine.template.domain.useCases.GetLoginInfo
import org.koin.dsl.module

val useCaseModule = module {
  single {
    GetLoginInfo(get())
  }
}