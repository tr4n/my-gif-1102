package com.example.mygif1102.networks

import com.example.mygif1102.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface IGiphyService {
    @GET("trending")
    fun getTrendings(
        @Query(Constants.QUERY_API_KEY) apiKey: String,
        @Query(Constants.QUERY_LIMIT) limit: Int,
        @Query(Constants.QUERY_OFFSET) offset: Int
    ): Call<GifsResponse>

    @GET("search")
    fun getSearches(
        @Query(Constants.QUERY_API_KEY) apiKey: String,
        @Query("q") q: String,
        @Query(Constants.QUERY_LIMIT) limit: Int,
        @Query(Constants.QUERY_OFFSET) offset: Int
    ): Call<GifsResponse>

    @GET("{gif_id}")
    fun getGif(
        @Path("gif_id") gifId: String,
        @Query(Constants.QUERY_API_KEY) apiKey: String
    ): Call<GifResponse>

    companion object {
        val instance: IGiphyService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://api.giphy.com/v1/gifs/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(IGiphyService::class.java)
        }
    }
}