package com.example.mygif1102.http

import android.os.AsyncTask
import com.example.mygif1102.model.UrlRequest
import org.json.JSONException
import java.io.IOException

class RequestAsyncTask<T>(
    private val dataResponseHandler: DataResponseHandler<T>,
    private val onResponseListener: OnResponseListener<T>
) : AsyncTask<UrlRequest, Void, T?>() {
    private var exception: Exception? = null
    override fun doInBackground(vararg params: UrlRequest): T? {
        val urlRequest = params[0]
        var result: T? = null
        try {
            result = dataResponseHandler.getData(urlRequest)
            result ?: throw IOException()
        } catch (e: IOException) {
            exception = e
        } catch (e: JSONException) {
            exception = e
        }

        return result
    }

    override fun onPostExecute(result: T?) {
        exception?.let { onResponseListener.onFailed(it) }
            ?: result?.let { onResponseListener.onSuccess(it) }
    }
}
