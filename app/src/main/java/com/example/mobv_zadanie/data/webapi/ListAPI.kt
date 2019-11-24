package com.example.mobv_zadanie.data.webapi

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST



interface ListAPI{

        @POST("user/create.php")
        fun userRegister(@Body body: UserRequest): Call<UserResponse>
}