package com.a.utils

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val CHARSET = "UTF-8"
    const val TOKEN_EXPIRED = "401"
    const val PREF_TOKEN = "PREF_TOKEN"
    const val PREF_USERNAME = "PREF_USERNAME"
    const val PREF_PASSWORD = "PREF_PASSWORD"

    val CURRENT_TIME: String = SimpleDateFormat("dd-MM-hh-mm-ss", Locale.US).format(Calendar.getInstance().time)
}