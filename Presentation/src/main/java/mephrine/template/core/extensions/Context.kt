package mephrine.template.core.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast

val Context.layoutInflater: LayoutInflater
  get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun Context.showToast(msg: String, gravity: Int = Gravity.BOTTOM) {
  Toast.makeText(this, msg, Toast.LENGTH_SHORT).apply {
    setGravity(gravity, 0, 0)
  }.show()
}


fun Context.showToast(resId: Int, gravity: Int = Gravity.BOTTOM) {
  showToast(getString(resId), gravity)
}