package com.example.mobv_zadanie.data.firebaseapi

import android.util.Log
import com.example.mobv_zadanie.data.webapi.CallAPI
import okhttp3.Interceptor
import okhttp3.Response

class FcmAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        Log.i("TAG_API", "sending request")

        val server_key = "AAAAKaWAxVU:APA91bH0R3-4RY3xTjhNB97MgOHSweCAXdAW_qWM3eQ9d3d4hV8qHpKipCDlNi6gJTrrHmyeTe0l4yJ0jXJCF9q5G4d4ySZrvE7j2lytA8SUSVxqNeyE4EVDyTQvc-nQ2zuSg8fypsnt"

        val request = chain.request()
            .newBuilder()
            .addHeader("Accept","application/json")
            .addHeader("Content-Type","application/json")

        //boolean from CallAPI
        if (CallAPI.useAuthentication){
            request.addHeader("Authorization", "key=$server_key")
        }

        return chain.proceed(request.build())
    }
}