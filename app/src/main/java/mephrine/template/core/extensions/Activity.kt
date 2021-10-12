package mephrine.template.core.extensions

import android.app.Activity
import android.os.Build
import android.view.Gravity
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import java.lang.ref.WeakReference


fun Activity.removeStatusBar() {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    window.insetsController?.hide(WindowInsets.Type.statusBars())
  } else {
    @Suppress("DEPRECATION")
    window.setFlags(
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
  }
}

fun Activity.weakActivity(): Activity? {
  return WeakReference(this).get()
}