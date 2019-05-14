package com.example.mygif1102.http

import com.example.mygif1102.model.UrlRequest
import org.json.JSONException
import java.io.IOException


interface DataResponseHandler<T> {
    @Throws(IOException::class, JSONException::class)
    fun getData(request: UrlRequest): T?

    @Throws(JSONException::class)
    fun parseRawToData(rawData: String): T
}
