package mephrine.template.core.extensions

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import mephrine.template.BuildConfig
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun File.toMultipartBody(): MultipartBody.Part {
  return MultipartBody.Part.createFormData(
    "file",
    name,
    asRequestBody("multipart/form-data".toMediaTypeOrNull())
  )
}

fun File.scan(context: Context) {
  MediaScannerConnection.scanFile(context, arrayOf(toString()), arrayOf(name), null)
}

fun File.moveTo(newFile: File) {
  FileInputStream(this).use { `in` ->
    FileOutputStream(newFile).use { out ->
      val buf = ByteArray(1024)
      var len: Int
      while (`in`.read(buf).also { len = it } > 0) {
        out.write(buf, 0, len)
      }
      this.delete()
    }
  }
}

fun File.deleteIfExist(context: Context) {
  if (exists()) {
    delete()
    scan(context)
  }
}

fun File.toUri(context: Context): Uri =
  FileProvider.getUriForFile(
    context,
    BuildConfig.APPLICATION_ID + ".fileprovider",
    this
  )

fun String.getMimeType(): String? {
  val extension = MimeTypeMap.getFileExtensionFromUrl(this)

  return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
}