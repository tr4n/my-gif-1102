package com.example.mygif1102.http

import com.example.mygif1102.model.GifImage
import com.example.mygif1102.model.GifResponse
import org.json.JSONObject

class GifResponseHandler : BaseDataResponseHandler<GifResponse>() {
    override fun parseRawToData(rawData: String): GifResponse {
        val body = JSONObject(rawData)
        val data = body.getJSONObject("data")
        val imageObject = data.getJSONObject("images").getJSONObject("downsized_medium")

        return GifResponse(
            data.getString("title"),
            data.getString("source_tld"),
            GifImage(
                data.getString("id"),
                imageObject.getString("width").toInt(),
                imageObject.getString("height").toInt(),
                imageObject.getString("url")
            )
        )
    }
}
