package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wifirooms")
data class WifiRoomItem(@PrimaryKey var ssid: String) {
    var posts: MutableList<PostItem> = ArrayList()

    override fun toString(): String {
        return "WifiRoomItem(ssid='$ssid', posts=$posts)"
    }
}