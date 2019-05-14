package com.example.mygif1102.model


const val METHOD_GET = "GET"
const val METHOD_POST = "POST"

data class ApiRequest(val method: String = METHOD_GET, val urlRequest: UrlRequest)
