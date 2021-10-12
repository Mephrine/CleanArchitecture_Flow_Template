package mephrine.template.features.loggedIn.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import mephrine.template.R
import mephrine.template.core.presentations.base.BaseActivity
import mephrine.template.core.presentations.base.EmptyViewModel
import mephrine.template.databinding.ActivityLoggedInBinding

class LoggedInActivity(
  override val viewModel: EmptyViewModel
  ): BaseActivity<ActivityLoggedInBinding, EmptyViewModel>(R.layout.activity_logged_in) {
  companion object {
    fun make(context: Context, userName: String): Intent = Intent(context, LoggedInActivity::class.java).apply {
      putExtra("userName", userName)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    onBind()
  }

  private fun onBind() {
    binding.userName = intent.getStringExtra("userName")
  }
}