package com.example.mygif1102.networks

import com.google.gson.annotations.SerializedName

class GifResponse(@SerializedName("data") val data: DataJson) {
    inner class DataJson(
        @SerializedName("id") val id: String,
        @SerializedName("source_tld") val sourceTld: String,
        @SerializedName("title") val title: String,
        @SerializedName("images") val images: ImageJson
    ) {
        inner class ImageJson(
            @SerializedName("original") val original: OriginalJSON
        ) {
            inner class OriginalJSON(val width: String, val height: String, val url: String)
        }
    }
}