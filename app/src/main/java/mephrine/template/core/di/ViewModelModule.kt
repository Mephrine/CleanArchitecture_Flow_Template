package mephrine.template.core.di


import mephrine.template.features.loggedOut.presentation.viewModels.LoggedOutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

  viewModel<LoggedOutViewModel> {
    LoggedOutViewModel(get())
  }
}