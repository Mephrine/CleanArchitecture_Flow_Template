package mephrine.template.core.di

import mephrine.template.core.preference.Preference
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
  single {
    Preference(androidContext())
  }
}