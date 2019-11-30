package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import kotlin.collections.ArrayList

@Entity(tableName = "wifirooms")
data class WifiRoomItem(@PrimaryKey val ssid: String, val time: Date) {
    var posts: MutableList<PostItem> = ArrayList()

    override fun toString(): String {
        return "WifiRoomItem(ssid='$ssid', posts=$posts)"
    }
}