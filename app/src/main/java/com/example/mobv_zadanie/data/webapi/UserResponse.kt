package com.example.mobv_zadanie.data.webapi

//used for storing server response, API: registerUser, loginUser, refresh
data class UserResponse(val uid: String, val access: String, val refresh: String)