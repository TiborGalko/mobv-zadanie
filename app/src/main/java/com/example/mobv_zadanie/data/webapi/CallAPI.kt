package com.example.mobv_zadanie.data.webapi

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface CallAPI{

    companion object {

        private const val BASE_URL = "http://zadanie.mpage.sk/"
        val type : ListAPI
                get() {
                    return create()
            }


        fun create(): ListAPI {
            Log.i("TAG_API", "creating API method")
            //todo gson

            val gson = GsonBuilder().setLenient().create()

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .authenticator(TokenAuthenticator())
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