package com.example.mobv_zadanie.data.webapi.model

import java.sql.Timestamp

data class MessageListReposne constructor(var uid: String, var contact:String, var message:String, val time: Timestamp, var uid_name:String, var contact_name:String, var uid_fid:String, var contact_fid:String)