package mephrine.template.core.coroutines

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import mephrine.template.BuildConfig
import kotlin.coroutines.CoroutineContext

class UICoroutineScope
  : BaseCoroutineScope {
  override val job: Job = Job()
  private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    throwable.printStackTrace()
  }

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + coroutineExceptionHandler + job

  override val ioDispatchers: CoroutineContext
    get() = Dispatchers.IO + coroutineExceptionHandler


  override fun releaseCoroutine() {
    if (BuildConfig.DEBUG) {
      Log.d("UICoroutineScope", "onRelease coroutine")
    }
    job.cancel()
  }
}