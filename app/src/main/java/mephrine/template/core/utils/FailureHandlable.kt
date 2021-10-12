package mephrine.template.core.utils

import android.content.Context
import mephrine.template.R
import mephrine.template.core.errors.Failure
import mephrine.template.core.extensions.showToast

interface FailureHandlable {
  fun observeFailure(context: Context, failure: Failure) =
    with(context) {
      when (failure) {
        is Failure.NetworkConnectionFailure -> showToast(getString(R.string.network_not_found))
        is Failure.ServerFailure -> showToast(getString(R.string.server_request_error))
        else -> showToast(getString(R.string.unknown_error))
      }
    }
}