package com.example.mobv_zadanie.data.webapi

import com.example.mobv_zadanie.data.webapi.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * userRegister and userLogin - use same logic
 * userRefresh  - call this method when received error response 401 - load uid, refresh and save access, refresh tokens via ../util/SharedPref.kt
 *              - same response body as userRegister or userLogin
 *
 * roomList - getting rooms from api
 * other API calls should use access token from
 */


interface ListAPI{

        @POST("user/create.php")
        fun userRegister(@Body body: UserRequest): Call<UserResponse>

        @POST("user/login.php")
        fun userLogin(@Body body: UserRequest): Call<UserResponse>

        @POST("user/refresh.php")
        fun userRefresh(@Body body: UserRefresh): Call<UserResponse>

        @POST("room/list.php")
        suspend fun roomList(@Body body: RoomListRequest): Call<List<RoomListResponse>>

        @POST("contact/list.php")
        suspend fun contactList(@Body body: ContactListRequest): Response<List<ContactListResponse>>

        @POST("room/read.php")
        suspend fun messagesList(@Body body: RoomMessagesListRequest): Response<List<RoomMessagesListResponse>>

        @POST("room/message.php")
        suspend fun message(@Body body: RoomMessageRequest): Response<Any>

}