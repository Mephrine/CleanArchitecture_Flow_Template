package mephrine.template.core.models

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mephrine.template.core.errors.ServerError

data class ResultListDTO<T> (
  @SerializedName("status_code")
  val statusCode: String?,

  @SerializedName("status_message")
  val statusMessage: String?,

  @SerializedName("error_code")
  val errorCode: String?,

  @SerializedName("error_message")
  val errorMessage: String?,

  @SerializedName("result_cnt")
  val resultCnt: String?,

  @SerializedName("result_yn")
  val resultYn: String?,

  @SerializedName("result_data")
  val resultData: List<T>?
) {
  fun responseToFlow(
    validation: (ResultListDTO<T>) -> Boolean = ::validatesStatusAndResultData,
    throwableError: (ResultListDTO<T>) -> Nothing = ::throwsServerError
  ): Flow<ResultListDTO<T>> = flow {
    if (validation(this@ResultListDTO)) {
      emit(this@ResultListDTO)
    } else {
      throwableError(this@ResultListDTO)
    }
  }

  fun responseToCompletableFlow(
    validation: (ResultListDTO<T>) -> Boolean = ::validatesStatusAndResultData,
    throwableError: (ResultListDTO<T>) -> Nothing = ::throwsServerError
  ): Flow<Boolean> = flow {
    if (validation(this@ResultListDTO)) {
      emit(true)
    } else {
      throwableError(this@ResultListDTO)
    }
  }

  private fun validatesStatusAndResultData(response: ResultListDTO<T>) = response.statusCode == "200" && null != response.resultData
  private fun throwsServerError(response: ResultListDTO<T>): Nothing = throw ServerError(errorMessage)
}

data class ResultDTO<T>(
  @SerializedName("status_code")
  val statusCode: String?,

  @SerializedName("status_message")
  val statusMessage: String?,

  @SerializedName("error_code")
  val errorCode: String?,

  @SerializedName("error_message")
  val errorMessage: String?,

  @SerializedName("result_cnt")
  val resultCnt: String?,

  @SerializedName("result_yn")
  val resultYn: String?,

  @SerializedName("result_data")
  val resultData: T?
) {
  fun responseToFlow(
    validation: (ResultDTO<T>) -> Boolean = ::validatesStatusAndResultData,
    throwableError: (ResultDTO<T>) -> Nothing = ::throwsServerError
  ): Flow<T> = flow {
    if (validation(this@ResultDTO)) {
      emit(resultData!!)
    } else {
      throwableError(this@ResultDTO)
    }
  }

  fun responseToCompletableFlow(
    validation: (ResultDTO<T>) -> Boolean = ::validatesStatusAndResultData,
    throwableError: (ResultDTO<T>) -> Nothing = ::throwsServerError
  ): Flow<Boolean> = flow {
    if (validation(this@ResultDTO)) {
      emit(true)
    } else {
      throwableError(this@ResultDTO)
    }
  }

  private fun validatesStatusAndResultData(response: ResultDTO<T>) = response.statusCode == "200" && null != response.resultData
  private fun throwsServerError(response: ResultDTO<T>): Nothing = throw ServerError(errorMessage)
}


sealed class ResponseData<out O> {
  open class IncludeCountNumberList<out O>(val response: List<O>?, val count: String?) :
    ResponseData<O>()
}