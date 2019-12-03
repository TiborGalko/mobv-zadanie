package com.example.mobv_zadanie.data.webapi

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.mobv_zadanie.ui.LoginFragment
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface CallAPI{

    companion object {

        private const val BASE_URL = "http://zadanie.mpage.sk/"
        const val api_key : String = "c95332ee022df8c953ce470261efc695ecf3e784"

        var useAuthentication : Boolean = false
        val type : ListAPI
                get() {
                    return create()
            }

        fun setAuthentication(value: Boolean){
            useAuthentication = value
        }

        fun create(): ListAPI {

            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(AuthInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(ListAPI::class.java)
        }
    }
}