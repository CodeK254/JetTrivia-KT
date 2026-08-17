package com.tamara.jettrivia.util

import android.util.Log

fun logger(
    message: String,
    label: String = Constants.APP_NAME,
): Unit{
    Log.d(label, "Details: $message")
}