package mephrine.template.core.presentations.views

import android.app.Dialog
import android.content.Context
import android.graphics.PorterDuff
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import mephrine.template.R

object LoadingIndicator {
  private var dialog: Dialog? = null

  fun showLoading(context: Context) {
    if (dialog == null) {
      dialog = Dialog(context, R.style.CustomIndicator).apply {
        addContentView(initProgressBar(context), initParams())
        setCancelable(false)
      }
    }

    dialog?.show()
  }

  private fun initProgressBar(context: Context) = ProgressBar(context).apply {
    isIndeterminate = true
    indeterminateDrawable.setColorFilter(
      ContextCompat.getColor(context, R.color.blue_0089ff),
      PorterDuff.Mode.MULTIPLY
    )
  }

  private fun initParams() = ViewGroup.LayoutParams(
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.MATCH_PARENT
  )

  val isShowing: Boolean
    get() = dialog?.isShowing ?: false

  fun hideLoading() {
    dialog?.dismiss()
    dialog = null
  }
}