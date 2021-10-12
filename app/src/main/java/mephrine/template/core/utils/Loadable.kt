package mephrine.template.core.utils

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mephrine.template.core.presentations.views.LoadingIndicator

interface Loadable {
  fun observeLoading(context: Context, isLoading: Boolean) {
    if (isLoading) {
      LoadingIndicator.showLoading(context)
    } else {
      LoadingIndicator.hideLoading()
    }
  }
}