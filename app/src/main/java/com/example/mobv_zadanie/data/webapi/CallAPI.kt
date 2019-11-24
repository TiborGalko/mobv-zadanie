package com.example.mobv_zadanie.data.webapi

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

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
            Log.i("TAG_API", "creating API method")
            /*
            val contentInterceptor : Interceptor = object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("User-Agent", "Zadanie-Android/1.0.0")
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                    return chain.proceed(request.build())
                }
            }
            */

            val gson = GsonBuilder().setLenient().create()

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