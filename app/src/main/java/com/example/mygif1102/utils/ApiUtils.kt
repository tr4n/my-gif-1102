package com.example.mygif1102.utils

import com.example.mygif1102.model.ApiRequest
import com.example.mygif1102.model.ApiResponse
import com.google.gson.JsonIOException
import java.io.*
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

fun Map<String, Any>.toQueryString(): String {
    return StringBuilder().also {
        this.forEach { entry ->
            it.apply {
                append(entry.key)
                append("=")
                append(URLEncoder.encode(entry.value.toString(), "UTF-8"))
                append("&")
            }
        }
    }.toString()
}
