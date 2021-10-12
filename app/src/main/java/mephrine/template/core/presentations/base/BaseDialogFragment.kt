package mephrine.template.core.presentations.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import mephrine.template.R
import mephrine.template.core.presentations.views.LoadingIndicator

abstract class BaseDialogFragment<B : ViewDataBinding, VM : BaseViewModel>(
  private val layoutId: Int,
) : DialogFragment() {
  lateinit var binding: B
  abstract val viewModel: VM
  protected open val transitionAnimation = BaseDialog.TransitionAnimation.FADE_IN

  //==============================================================================================
  // Init
  //==============================================================================================
  /**
   * 바인딩 구현
   */
  abstract fun onBind()

  /**
   * 뷰 관련 초기값 세팅
   */
  abstract fun initView()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NO_TITLE, R.style.DialogTheme)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    setTransitionAnimation(transitionAnimation)
    binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onBind()
    initView()
    changeToExitDialogAnimation()
  }

  //==============================================================================================
  // Overrides
  //==============================================================================================
  protected open fun observeLoading(isLoading: Boolean) {
    when (isLoading) {
      true -> {
        LoadingIndicator.showLoading(requireActivity())
      }
      false -> {
        LoadingIndicator.hideLoading()
      }
    }
  }

  //==============================================================================================
  // Animation
  //==============================================================================================
  private fun changeToExitDialogAnimation() {
    dialog?.setOnShowListener {
      Handler(Looper.getMainLooper()).post { changeExitAnimation() }
    }
  }

  private fun changeExitAnimation() {
    when (transitionAnimation) {
      BaseDialog.TransitionAnimation.POPUP_ENTER -> setTransitionAnimation(BaseDialog.TransitionAnimation.POPUP_EXIT)
      BaseDialog.TransitionAnimation.FADE_IN -> setTransitionAnimation(BaseDialog.TransitionAnimation.FADE_OUT)
      else -> {
      }
    }
  }

  private fun setTransitionAnimation(animation: BaseDialog.TransitionAnimation) {
    dialog?.window?.setWindowAnimations(animation.style)
  }
}