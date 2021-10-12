package mephrine.template.core.extensions

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

val String.nullIfEmpty: String?
  get() = if (this.isEmpty()) {
    null
  } else {
    this
  }