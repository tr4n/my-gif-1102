package com.example.mygif1102.utils

import android.content.res.Resources
import com.example.mygif1102.R

const val LIMIT = 25
const val EXTRA_TITLE = "com.example.mygif1102.utils.EXTRA_TITLE"
const val EXTRA_GIF = "com.example.mygif1102.utils.EXTRA_GIF"
const val MESSAGE_TYPE_TITLE = 1
const val MESSAGE_TYPE_GIF = 2

const val QUERY_API_KEY = "api_key"
const val QUERY_Q = "q"
const val QUERY_LIMIT = "limit"
const val QUERY_OFFSET = "offset"

const val BASE_URL_GIPHY = "http://api.giphy.com/v1/gifs"

const val READ_TIMEOUT = 15000
const val CONNECTION_TIMEOUT = 15000

val COLORS = intArrayOf(
    R.color.yellow,
    R.color.fuchsia,
    R.color.red,
    R.color.silver,
    R.color.gray,
    R.color.gray,
    R.color.olive,
    R.color.purple,
    R.color.maroon,
    R.color.aqua,
    R.color.lime,
    R.color.teal,
    R.color.green,
    R.color.blue,
    R.color.navy
)

val SCREEN_WIDTH = Resources.getSystem().displayMetrics.widthPixels
val SCREEN_HEIGHT = Resources.getSystem().displayMetrics.heightPixels