package mephrine.template.utility

import android.util.Log

object L {
  // log tag
  private const val TAG = "Mephrine"

  // 개발계 빌드 시 에만 로그 노출
  private val isDev = BuildConfig.DEBUG

  /**
   * debug
   * msg : 로그 메시지
   */
  @JvmStatic
  fun d(msg: String) {
    d(TAG, msg)
  }

  @JvmStatic
  fun d(tag: String, msg: String) {
    if (isDev) {
      try {
        Log.d(tag, msg)
      } catch (e: Exception) {
        Log.e(tag, "error : $e")
      }

    }
  }

  /**
   * info
   * msg : 로그 메시지
   */
  @JvmStatic
  fun i(msg: String) {
    i(TAG, msg)
  }

  @JvmStatic
  fun i(tag: String, msg: String) {
    if (isDev) {
      try {
        Log.i(tag, msg)
      } catch (e: Exception) {
        Log.e(tag, "error : $e")
      }
    }
  }

  /**
   * verbose
   * msg : 로그 메시지
   */
  @JvmStatic
  fun v(msg: String) {
    v(TAG, msg)
  }

  @JvmStatic
  fun v(tag: String, msg: String) {
    if (isDev) {
      try {
        Log.v(tag, msg)
      } catch (e: Exception) {
        Log.e(tag, "error : $e")
      }
    }
  }

  /**
   * warning
   * msg : 로그 메시지
   */
  @JvmStatic
  fun w(msg: String) {
    w(TAG, msg)
  }

  @JvmStatic
  fun w(tag: String, msg: String) {
    if (isDev) {
      try {
        Log.w(
          tag,
          "======================================================================"
        )
        Log.w(
          tag,
          "${Throwable().stackTrace[1].className}[Line = ${Throwable().stackTrace[1].lineNumber}]\n"
        )
        Log.w(tag, msg)
        Log.w(
          tag,
          "======================================================================"
        )
      } catch (e: Exception) {
        Log.e(tag, "error : $e")
      }
    }
  }

  /**
   * error
   * msg : 로그 메시지
   */
  @JvmStatic
  fun e(msg: String) {
    e(TAG, msg)
  }

  @JvmStatic
  fun e(t: Throwable?) {
    if (isDev) {
      t?.apply { printStackTrace() }
    }
  }

  @JvmStatic
  fun e(tag: String, msg: String) {
    if (isDev) {
      try {
        Log.e(
          tag,
          "======================================================================"
        )
        Log.e(
          tag,
          "${Throwable().stackTrace[1].className}[Line = ${Throwable().stackTrace[1].lineNumber}]\n"
        )

        Log.e(tag, msg)

        Log.e(
          tag,
          "======================================================================"
        )
      } catch (e: Exception) {
        Log.e(tag, "error : $e")
      }
    }

  }
}