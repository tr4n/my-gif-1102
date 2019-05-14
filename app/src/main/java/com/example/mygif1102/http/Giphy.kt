package com.example.mygif1102.http

import com.example.mygif1102.model.*
import com.example.mygif1102.utils.*
import org.json.JSONObject


class Giphy private constructor() : GIPHYGetRequest {
    override fun getTrendingGifs(
        apiKey: String,
        limit: Int,
        offset: Int,
        onResponseListener: OnResponseListener<GifsResponse>
    ) {
        RequestAsyncTask(GifsResponseHandler(), onResponseListener).execute(
            UrlRequest(
                BASE_URL_GIPHY,
                "trending",
                mapOf(
                    QUERY_API_KEY to apiKey,
                    QUERY_LIMIT to limit,
                    QUERY_OFFSET to offset
                ).toQueryString()
            )
        )
    }

    override fun getSearches(
        apiKey: String,
        q: String,
        limit: Int,
        offset: Int,
        onResponseListener: OnResponseListener<GifsResponse>
    ) {
        RequestAsyncTask(GifsResponseHandler(), onResponseListener).execute(
            UrlRequest(
                BASE_URL_GIPHY,
                "search",
                mapOf(
                    QUERY_API_KEY to apiKey,
                    QUERY_Q to q,
                    QUERY_LIMIT to limit,
                    QUERY_OFFSET to offset
                ).toQueryString()
            )
        )
    }

    override fun getGif(gifId: String, apiKey: String, onResponseListener: OnResponseListener<GifResponse>) {
        RequestAsyncTask(GifResponseHandler(), onResponseListener).execute(
            UrlRequest(
                BASE_URL_GIPHY,
                gifId,
                mapOf(
                    QUERY_API_KEY to apiKey
                ).toQueryString()
            )
        )
    }

    companion object {
        val instance: Giphy by lazy {
            Giphy()
        }
    }
}
