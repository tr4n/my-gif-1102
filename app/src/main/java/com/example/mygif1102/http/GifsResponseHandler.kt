package com.example.mygif1102.http

import com.example.mygif1102.model.GifImage
import com.example.mygif1102.model.GifsResponse
import org.json.JSONObject

class GifsResponseHandler : BaseDataResponseHandler<GifsResponse>() {
    override fun parseRawToData(rawData: String): GifsResponse {
        val body = JSONObject(rawData)
        val datas = body.getJSONArray("data")
        val gifs = ArrayList<GifImage>().apply {
            for (index in 0 until datas.length()) {
                val dataObject = datas.getJSONObject(index)
                val imageObject =
                    dataObject.getJSONObject("images").getJSONObject("fixed_width_downsampled")

                add(
                    GifImage(
                        dataObject.getString("id"),
                        imageObject.getString("width").toInt(),
                        imageObject.getString("height").toInt(),
                        imageObject.getString("url")
                    )
                )
            }
        }
        return GifsResponse(gifs)
    }
}