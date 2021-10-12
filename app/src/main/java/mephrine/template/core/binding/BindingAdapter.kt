package mephrine.template.core.binding

import android.view.View
import androidx.databinding.BindingAdapter
import mephrine.template.core.extensions.debounce

object BindingAdapter {
  @BindingAdapter("debounce")
  @JvmStatic
  fun setDebouncedListener(view: View, onClickListener: View.OnClickListener)
    = view.debounce(300L) { onClickListener.onClick(view) }
}