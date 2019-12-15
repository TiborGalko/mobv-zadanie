package com.example.mobv_zadanie.data.firebaseapi

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface FcmAPI {

    companion object {

        private const val BASE_URL = "https://fcm.googleapis.com/"

        var useAuthentication : Boolean = true
        val type : FcmListAPI
            get() {
                return create()
            }

        fun setAuthentication(value: Boolean){
            useAuthentication = value
        }

        fun create(): FcmListAPI {

            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(FcmAuthInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(FcmListAPI::class.java)
        }
    }
}