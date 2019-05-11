package com.example.mygif1102.networks

import com.google.gson.annotations.SerializedName

class GifsResponse(@SerializedName("data") val datas: List<DataJson>) {
    inner class DataJson(val id: String, val images: ImageJson) {
        inner class ImageJson(@SerializedName("fixed_width_downsampled") val fixedWidthJson: FixedWidthJson) {
            inner class FixedWidthJson(val width: String, val height: String, val url: String)
        }
    }
}