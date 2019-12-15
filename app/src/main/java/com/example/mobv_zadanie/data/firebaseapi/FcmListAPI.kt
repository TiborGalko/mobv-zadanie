package com.example.mobv_zadanie.data.firebaseapi

import com.example.mobv_zadanie.data.firebaseapi.model.MessageSendRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface FcmListAPI {
    @POST("fcm/send")
    suspend fun postFcmMessage(@Body body: MessageSendRequest)
}