package mephrine.template.core.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.CheckResult
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun View.cancelTransition() {
  transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
  this.visibility = View.VISIBLE
}

fun View.invisible() {
  this.visibility = View.GONE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
  LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadFromUrl(url: String) =
  Glide.with(this.context.applicationContext)
    .load(url)
    .transition(DrawableTransitionOptions.withCrossFade())
    .into(this)


@ObsoleteCoroutinesApi
fun View.debounce(delayMillis: Long = 300L, action: suspend (View) -> Unit) {
  val event = GlobalScope.actor<View>(Dispatchers.Main) {
    for (event in channel) {
      action(event)
      delay(delayMillis)
    }
  }
  setOnClickListener {
    event.offer(it)
  }
}

internal fun <T> SendChannel<T>.safeOffer(element: T): Boolean {
  return runCatching { offer(element) }.getOrDefault(false)
}

@ExperimentalCoroutinesApi
@CheckResult
fun View.clicks(): Flow<View> {
  return callbackFlow {
    setOnClickListener { safeOffer(it) }
    awaitClose { setOnClickListener(null) }
  }
}

class RecyclerViewItemDecoration(private val space: Int, private val isHorizontal: Boolean) :
  RecyclerView.ItemDecoration() {
  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    super.getItemOffsets(outRect, view, parent, state)
    if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1)
      if (isHorizontal) {
        outRect.right = space
      } else {
        outRect.bottom = space
      }
  }
}

@SuppressLint("ClickableViewAccessibility")
fun View.setTouchable(to: Boolean) =
  when (to) {
    true -> {
      setOnTouchListener { view, motionEvent -> view.onTouchEvent(motionEvent) }
    }
    false -> {
      setOnTouchListener { _, _ -> true }
    }
  }


@SuppressLint("ClickableViewAccessibility")
fun ViewGroup.setTouchableInParent(to: Boolean) {
  children.iterator().forEach {
    (it as? ViewGroup)?.also { viewGroup ->
      when (viewGroup) {
        is WebView -> viewGroup.setTouchable(to)
        is RecyclerView -> viewGroup.setTouchable(to)
        is NestedScrollView, is ScrollView -> {
          viewGroup.setTouchable(to)
          viewGroup.setTouchableInParent(to)
        }
        else -> viewGroup.setTouchableInParent(to)
      }
    } ?: it.setTouchable(to)
  }
}

fun SwipeRefreshLayout.startRefresh() {
  setTouchableInParent(false)
}

fun SwipeRefreshLayout.endRefresh() {
  isRefreshing = false
  setTouchableInParent(true)
}


fun View?.hideKeyboard(context: Context) {
  this?.apply {
    val inputMethodManager =
      context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
  }
}

fun View?.showKeyboard(context: Context) {
  this?.apply {
    val inputMethodManager =
      context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, 0)
  }
}