package mephrine.template.core.presentations.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import mephrine.template.R
import mephrine.template.core.presentations.views.LoadingIndicator


abstract class BaseDialog<B : ViewDataBinding>(
  context: Context,
  layoutId: Int,
  private val onDismiss: (() -> Unit)? = null
) : Dialog(context) {
  protected val binding: B by lazy {
    DataBindingUtil.inflate<B>(layoutInflater, layoutId, null, false)
  }

  enum class TransitionAnimation(var style: Int) {
    FADE_IN(R.style.FadeInTransitionAnimation),
    FADE_OUT(R.style.FadeOutTransitionAnimation),
    POPUP_ENTER(R.style.PopupTransitionAnimationEnter),
    POPUP_EXIT(R.style.PopupTransitionAnimationExit)
  }

  protected open val transitionAnimation = TransitionAnimation.POPUP_ENTER

  //==============================================================================================
  // Constructor
  //==============================================================================================
  init {
    setThemeAndOwner(context)
  }

  private fun setThemeAndOwner(context: Context) {
    when (context) {
      is Activity -> setOwnerActivity(context)
      is Fragment -> setOwnerActivity(context.requireActivity())
    }

    super.getContext().setTheme(R.style.DialogTheme)
  }

  //==============================================================================================
  // Init
  //==============================================================================================
  abstract fun onBind()

  abstract fun initView()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    onBind()
    initView()
    changeToExitDialogAnimation()
  }

  //==============================================================================================
  // Overrides
  //==============================================================================================
  override fun dismiss() {
    super.dismiss()
    onDismiss?.invoke()
  }

  protected open fun observeLoading(isLoading: Boolean) {
    if (isLoading) {
      LoadingIndicator.showLoading(ownerActivity ?: context)
    } else {
      LoadingIndicator.hideLoading()
    }
  }

  //==============================================================================================
  // Animation
  //==============================================================================================
  private fun changeToExitDialogAnimation() {
    setOnShowListener {
      Handler(Looper.getMainLooper()).post { changeExitAnimation() }
    }
  }

  private fun changeExitAnimation() {
    when (transitionAnimation) {
      TransitionAnimation.POPUP_ENTER -> setTransitionAnimation(TransitionAnimation.POPUP_EXIT)
      TransitionAnimation.FADE_IN -> setTransitionAnimation(TransitionAnimation.FADE_OUT)
      else -> {}
    }
  }

  private fun setTransitionAnimation(animation: TransitionAnimation) {
    window?.setWindowAnimations(animation.style)
  }
}