package mephrine.fixture

import com.google.gson.Gson

inline fun <reified T : Any> String.fixture(): T = Gson().fromJson(this, T::class.java)


fun String.removeLines() = replace("\n", "")