package mephrine.template.features.loggedOut.presentation.views

import android.os.Bundle
import android.widget.Toast
import mephrine.template.R
import mephrine.template.core.errors.Failure
import mephrine.template.core.extensions.hideKeyboard
import mephrine.template.core.navigation.Navigator
import mephrine.template.core.presentations.base.BaseActivity
import mephrine.template.core.presentations.views.LoadingIndicator
import mephrine.template.databinding.ActivityLoggedOutBinding
import mephrine.template.features.loggedOut.presentation.viewModels.LoggedOutViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoggedOutActivity: BaseActivity<ActivityLoggedOutBinding, LoggedOutViewModel>(R.layout.activity_logged_out) {
  override val viewModel: LoggedOutViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    onBind()
    bindUI()
  }

  fun hideKeyboard()
    = currentFocus.hideKeyboard(this)


  private fun onBind()
    = with(binding) {
      binding.view = this@LoggedOutActivity
      binding.viewModel = this@LoggedOutActivity.viewModel
    }

  private fun bindUI()
    = with(viewModel) {
      isLoading.observe(this@LoggedOutActivity) {
        when(it) {
          true -> {
            hideKeyboard()
            LoadingIndicator.showLoading(this@LoggedOutActivity)
          }
          false -> LoadingIndicator.hideLoading()
        }
      }
    failure.observe(this@LoggedOutActivity, ::occursError)
      state.observe(this@LoggedOutActivity) {
        when(it) {
          is LoggedOutViewModel.State.LoggedIn -> login(it.userName)
          is LoggedOutViewModel.State.InvalidInputData -> failedLogIn()
        }
      }
    }

  private fun occursError(failure: Failure) {
    when(failure) {
      is Failure.NetworkConnectionFailure
        -> Toast.makeText(this, getString(R.string.disconnected_network_message), Toast.LENGTH_SHORT).show()
      is Failure.ServerFailure
        -> Toast.makeText(this, getString(R.string.failed_connect_server_message, failure.message), Toast.LENGTH_SHORT).show()
      is Failure.CacheFailure
        -> Toast.makeText(this, getString(R.string.cache_error_message), Toast.LENGTH_SHORT).show()

    }
  }

  private fun login(userName: String)
    = Navigator.routeToLoggedIn(this, userName)

  private fun failedLogIn()
    = Toast.makeText(this, getString(R.string.failed_login_toast_message), Toast.LENGTH_SHORT).show()
}