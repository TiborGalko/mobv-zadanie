package com.example.mobv_zadanie.data.webapi

import com.example.mobv_zadanie.data.webapi.model.*
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
        suspend fun userRegister(@Body body: UserRequest): Response<UserResponse>

        @POST("user/login.php")
        suspend fun userLogin(@Body body: UserRequest): Response<UserResponse>

        @POST("user/refresh.php")
        suspend fun userRefresh(@Body body: UserRefresh): Response<UserResponse>

        @POST("room/list.php")
        suspend fun roomList(@Body body: RoomListRequest): Response<List<RoomListResponse>>

        @POST("contact/list.php")
        suspend fun contactList(@Body body: ContactListRequest): Response<List<ContactListResponse>>

        @POST("room/read.php")
        suspend fun postList(@Body body: PostListRequest): Response<List<PostListResponse>>

        @POST("room/message.php")
        suspend fun postMessage(@Body body: PostRequest)

        @POST("contact/message.php")
        suspend fun postChatMessage(@Body body: MessageRequest)

        @POST("contact/read.php")
        suspend fun chatList(@Body body: MessageListRequest): Response<List<MessageListResponse>>

        @POST("user/fid.php")
        suspend fun postUserFid(@Body body: UserFidRequest)
}