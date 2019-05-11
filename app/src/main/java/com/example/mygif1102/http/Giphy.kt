package com.example.mygif1102.http

import com.example.mygif1102.Constants
import com.example.mygif1102.Constants.Companion.QUERY_API_KEY
import com.example.mygif1102.model.GifImage
import com.example.mygif1102.networks.GifResponse
import com.example.mygif1102.networks.GifsResponse
import com.example.mygif1102.networks.IGiphyService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Giphy : GIPHYGetRequest {

    private val baseUrl = "http://api.giphy.com/v1/gifs/"

    override fun getTrendings(
        apiKey: String,
        limit: Int,
        offset: Int,
        onGetResult: (ArrayList<GifImage>) -> Unit
    ) {
        val url =
            "${BASE_URL}trending?${Constants.QUERY_API_KEY}=$apiKey&${Constants.QUERY_LIMIT}=$limit&${Constants.QUERY_OFFSET}=$offset"
        val items = ArrayList<GifImage>()
        GetHttpRequestTask(onResponse = {
            val body = JSONObject(it)
            val datas = body.getJSONArray("data")
            for (index in 0 until datas.length() - 1) {
                val jsonObject = datas.getJSONObject(index)
                jsonObject.apply {
                    items.add(
                        GifImage(
                            getString("id"),
                            getJSONObject("images").getJSONObject("fixed_width").getString("width").toInt(),
                            getJSONObject("images").getJSONObject("fixed_width").getString("height").toInt(),
                            getJSONObject("images").getJSONObject("fixed_width").getString("url")
                        )
                    )
                }

            }
            onGetResult(items)
        }).execute(url)
    }

    override fun getSearches(
        apiKey: String,
        q: String,
        limit: Int,
        offset: Int,
        onGetResult: (ArrayList<GifImage>) -> Unit
    ) {
        val url =
            "${BASE_URL}search?$QUERY_API_KEY=$apiKey&$QUERY_Q=$q&$QUERY_LIMIT=$limit&$QUERY_OFFSET=$offset"
        val items = ArrayList<GifImage>()
        GetHttpRequestTask(onResponse = {
            val body = JSONObject(it)
            val datas = body.getJSONArray("data")
            for (index in 0 until datas.length() - 1) {
                val jsonObject = datas.getJSONObject(index)
                jsonObject.apply {
                    items.add(
                        GifImage(
                            getString("id"),
                            getJSONObject("images").getJSONObject("fixed_width").getString("width").toInt(),
                            getJSONObject("images").getJSONObject("fixed_width").getString("height").toInt(),
                            getJSONObject("images").getJSONObject("fixed_width").getString("url")
                        )
                    )
                }

            }
            onGetResult(items)
        }).execute(url)
    }

    override fun getGif(gifId: String, apiKey: String, onGetResult: (GifImage) -> Unit) {
        val url =
            "$BASE_URL$gifId?$QUERY_API_KEY=$apiKey"
        val item: GifImage
        GetHttpRequestTask(onResponse = {
            JSONObject(it).getJSONObject("data").let { jsonObject ->
                onGetResult(
                    GifImage(
                        jsonObject.getString("id"),
                        jsonObject.getJSONObject("images").getJSONObject("fixed_width").getString("width").toInt(),
                        jsonObject.getJSONObject("images").getJSONObject("fixed_width").getString("height").toInt(),
                        jsonObject.getJSONObject("images").getJSONObject("fixed_width").getString("url")
                    )
                )
            }
        }).execute(url)
    }

    companion object {
        private const val BASE_URL = "http://api.giphy.com/v1/gifs/"
        private const val QUERY_API_KEY = "api_key"
        private const val QUERY_LIMIT = "limit"
        private const val QUERY_OFFSET = "offset"
        private const val QUERY_Q = "q"
        private const val LIMIT = 25
        val instance: Giphy by lazy {
            Giphy()
        }
    }

}