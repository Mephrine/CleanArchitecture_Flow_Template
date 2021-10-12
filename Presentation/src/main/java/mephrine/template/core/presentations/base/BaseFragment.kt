package mephrine.template.core.presentations.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>(
  private val layoutId: Int
) : Fragment() {

  lateinit var binding: B
  abstract val viewModel: VM

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    onBind()
    initView()
  }

  open fun onBackPressed() {}

  /**
   * 바인딩 구현
   */
  abstract fun onBind()

  /**
   * 뷰 관련 초기값 세팅
   */
  abstract fun initView()
}
