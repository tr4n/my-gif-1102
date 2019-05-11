package com.example.mygif1102.http

import android.os.AsyncTask
import com.example.mygif1102.Constants
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class GetHttpRequestTask(private val onResponse: (jsonString: String) -> Unit) :
    AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg params: String?): String {
        val urlString = params[0]
        var inputLine: String?

        val connection = URL(urlString).openConnection() as HttpURLConnection

        //Set methods and timeouts
        connection.apply {
            requestMethod = Constants.METHOD_GET
            readTimeout = Constants.READ_TIMEOUT
            connectTimeout = Constants.CONNECTION_TIMEOUT
        }.connect()

        val streamReader = InputStreamReader(connection.inputStream)
        val bufferedReader = BufferedReader(streamReader)
        val stringBuilder = StringBuilder()
        inputLine = bufferedReader.readLine()
        while (inputLine != null) {
            stringBuilder.append(inputLine).append('\n')
            inputLine = bufferedReader.readLine()
        }
        bufferedReader.close()
        streamReader.close()
        connection.disconnect()
        return stringBuilder.toString()
    }

    override fun onPostExecute(result: String?) {
        result?.let { onResponse(result) }
    }
}
