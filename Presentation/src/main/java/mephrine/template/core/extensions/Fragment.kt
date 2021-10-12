package mephrine.template.core.extensions

import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
  beginTransaction().func().commit()

fun Fragment.showToast(msg: String, gravity: Int = Gravity.BOTTOM) {
  Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).apply {
    setGravity(gravity, 0, 0)
  }.show()
}

fun Fragment.showToast(resId: Int, gravity: Int = Gravity.BOTTOM) {
  showToast(requireContext().getString(resId), gravity)
}

fun Fragment.transactionReplace(layoutId: Int, fragment: Fragment) {
  childFragmentManager.beginTransaction().replace(layoutId, fragment).commit()
}