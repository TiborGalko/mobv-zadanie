package com.example.mobv_zadanie.data.webapi

import android.util.Log
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.data.webapi.CallAPI.Companion.useAuthentication
import com.example.mobv_zadanie.ui.LoginFragment
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        Log.i("TAG_API", "sending request")

        val request = chain.request()
            .newBuilder()
            .addHeader("User-Agent","Zadanie-Android/1.0.0")
            .addHeader("Accept","application/json")
            .addHeader("Content-Type","application/json")

        //boolean from CallAPI
        if (useAuthentication){
            request.addHeader("Authorization", SharedPrefWorker.getString(LoginFragment.loginContext,"access", "Token not restored"))
        }

        return chain.proceed(request.build())
    }
}