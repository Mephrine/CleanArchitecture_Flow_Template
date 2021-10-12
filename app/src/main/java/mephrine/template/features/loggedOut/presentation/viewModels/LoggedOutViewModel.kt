package mephrine.template.features.loggedOut.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import mephrine.template.core.coroutines.BaseCoroutineScope
import mephrine.template.core.coroutines.UICoroutineScope
import mephrine.template.core.presentations.base.BaseViewModelImpl
import mephrine.template.features.loggedOut.domain.useCases.GetLoginInfo

class LoggedOutViewModel(
  private val loginUseCase: GetLoginInfo,
  scope: BaseCoroutineScope = UICoroutineScope()
) : BaseViewModelImpl(),
  BaseCoroutineScope by scope {
  sealed class State {
    data class LoggedIn(val userName: String): State()
    object InvalidInputData: State()
  }

  private val _state = MutableLiveData<State>()
  val state: LiveData<State>
    get() = _state

  fun login(account: String, password: String) {
    if (!validate(account, password)) {
      _state.postValue(State.InvalidInputData)
      return
    }

    viewModelScope.launch {
      loginUseCase.execute(GetLoginInfo.Params(account, password))
        .onStart { _isLoading.postValue(true) }
        .catch { throwable ->
          _failure.postValue(throwable)
          _isLoading.postValue(false)
        }.collect {
          _isLoading.postValue(false)
          _state.postValue(State.LoggedIn(it.name))
        }
    }
  }

  private fun validate(account: String, password: String): Boolean =
    arrayOf(account, password)
      .map { it.isNotEmpty() }
      .reduce { total, text ->
        total && text
      }


  override fun clearViewModel() {
    releaseCoroutine()
  }
}