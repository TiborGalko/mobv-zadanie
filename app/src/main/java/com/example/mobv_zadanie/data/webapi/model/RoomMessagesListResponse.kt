package com.example.mobv_zadanie.data.webapi.model

import java.sql.Date

data class RoomMessagesListResponse constructor(val uid:String, val roomid:String, val message:String, val time:Date, val name:String)