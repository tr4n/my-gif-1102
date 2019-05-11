package com.example.mygif1102.http

import com.example.mygif1102.Constants
import com.example.mygif1102.model.GifImage
import com.example.mygif1102.networks.GifResponse
import com.example.mygif1102.networks.GifsResponse
import com.example.mygif1102.networks.IGiphyService
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GIPHYGetRequest {
    fun getTrendings(apiKey: String, limit: Int, offset: Int, onGetResult: (ArrayList<GifImage>) -> Unit)

    fun getSearches(apiKey: String, q: String, limit: Int, offset: Int, onGetResult: (ArrayList<GifImage>) -> Unit)

    fun getGif(gifId: String, apiKey: String, onGetResult: (GifImage) -> Unit)
}
