package com.example.mygif1102.http

import com.example.mygif1102.model.ApiResponse
import java.lang.Exception

interface OnResponseListener<T> {
     fun onSuccess(response: T)
     fun onFailed(exception: Exception)
}