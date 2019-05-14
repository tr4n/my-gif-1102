package com.example.mygif1102.http

import com.example.mygif1102.model.METHOD_GET
import com.example.mygif1102.model.UrlRequest
import org.json.JSONException
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


private val EXCEPTION_IO_HTTP = "HTTP error code: %s"

abstract class BaseDataResponseHandler<T> : DataResponseHandler<T> {

    @Throws(IOException::class, JSONException::class)
    override fun getData(urlRequest: UrlRequest): T? {

        var responseData: T?
        var connection: HttpURLConnection? = null
        try {
            connection = (URL(urlRequest.toString()).openConnection() as HttpURLConnection).apply {
                build(METHOD_GET)
                if (responseCode != HttpURLConnection.HTTP_OK) throw IOException(
                    String.format(
                        EXCEPTION_IO_HTTP,
                        responseCode
                    )
                )

                InputStreamReader(inputStream).run {
                    responseData = parseRawToData(readStream(this))
                    close()
                }
            }
        } finally {
            connection?.disconnect()
        }
        return responseData
    }
}

@Throws(IOException::class)
private fun HttpURLConnection.build(method: String){
    apply { requestMethod = method }.connect()
}

@Throws(IOException::class)
private fun readStream(stream: InputStreamReader): String {
    val reader = BufferedReader(stream)
    return StringBuilder().apply {
        var inputLine = reader.readLine()
        while (inputLine != null) {
            append(inputLine)
            inputLine = reader.readLine()
        }
        reader.close()
    }.toString()
}

