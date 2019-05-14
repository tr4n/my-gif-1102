package com.example.mygif1102.model

import java.lang.StringBuilder

private const val SEPARATION_PATH = "/"
private const val SEPARATION_QUERY = "?"

data class UrlRequest(val baseUrl: String, val path: String, val queryParams: String) {
    override fun toString(): String {
        return StringBuilder(baseUrl)
            .append(SEPARATION_PATH).append(path)
            .append(SEPARATION_QUERY).append(queryParams)
            .toString()
    }
}
