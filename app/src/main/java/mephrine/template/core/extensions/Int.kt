package mephrine.template.core.extensions

import android.content.Context
import android.util.DisplayMetrics
import java.text.NumberFormat

/**
 * numberFormat
 * return : String
 */
val Int.numberFormat
  get() = try {
    NumberFormat.getNumberInstance().format(this)
  } catch (e: Exception) {
    "0"
  }

fun Int.dpToPixel(context: Context) =
  this * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)