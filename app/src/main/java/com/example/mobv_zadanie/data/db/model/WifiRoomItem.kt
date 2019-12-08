package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import kotlin.collections.ArrayList

@Entity(tableName = "wifirooms")
data class WifiRoomItem(@PrimaryKey val ssid: String, val time: Timestamp, val uid: String) {
    var posts: MutableList<PostItem> = ArrayList()

    override fun toString(): String {
        return "WifiRoomItem(ssid='$ssid', posts=$posts)"
    }
}