package com.example.mobv_zadanie.data.webapi

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        Log.i("TAG_API", "sending request")

        val request = chain.request()
            .newBuilder()
            .addHeader("User-Agent","Zadanie-Android/1.0.0")
            .addHeader("Accept","application/json")
            .addHeader("Content-Type","application/json")

        /*
        if (chain.request().header("ZadanieApiAuth")?.compareTo("accept")==0) {
            val accessToken = //get stored accessToken
                request.addHeader("Authorization","Bearer $accessToken")
        }
        */

        return chain.proceed(request.build())
    }
}