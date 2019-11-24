package com.example.mobv_zadanie.data.webapi

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * userRegister and userLogin - use same logic
 * userRefresh  - call this method when received error response 401 - load uid, refresh and save access, refresh tokens via ../util/SharedPref.kt
 *              - same response body as userRegister or userLogin
 *
 * other API calls should use access token from
 */


interface ListAPI{

        @POST("user/create.php")
        fun userRegister(@Body body: UserRequest): Call<UserResponse>

        @POST("user/login.php")
        fun userLogin(@Body body: UserRequest): Call<UserResponse>

        @POST("user/refresh.php")
        fun userRefresh(@Body body: UserRefresh): Call<UserResponse>
}