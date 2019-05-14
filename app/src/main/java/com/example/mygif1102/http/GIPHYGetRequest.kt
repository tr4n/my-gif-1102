package com.example.mygif1102.http

import com.example.mygif1102.model.GifImage
import com.example.mygif1102.model.GifResponse
import com.example.mygif1102.model.GifsResponse

interface GIPHYGetRequest {
    fun getTrendingGifs(apiKey: String, limit: Int, offset: Int, onResponseListener: OnResponseListener<GifsResponse>)

    fun getSearches(apiKey: String, q: String, limit: Int, offset: Int, onResponseListener: OnResponseListener<GifsResponse>)

    fun getGif(gifId: String, apiKey: String, onResponseListener: OnResponseListener<GifResponse>)
}
