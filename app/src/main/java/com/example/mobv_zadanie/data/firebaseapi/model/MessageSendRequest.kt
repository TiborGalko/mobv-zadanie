package com.example.mobv_zadanie.data.firebaseapi.model

data class Data(val body: String, val title: String)

data class MessageSendRequest(val to: String, val data: Data)
//"to": "/topics/testkanal",
//    "data" : {
//      "body" : "This is a Firebase Cloud Messaging Topic Message!",
//      "title" : "FCM Message"
//    }