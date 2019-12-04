package com.example.mobv_zadanie.data.webapi.model

import java.sql.Timestamp

data class PostListResponse constructor(val uid: String, val roomid:String, val message:String, val time: Timestamp, val name:String)