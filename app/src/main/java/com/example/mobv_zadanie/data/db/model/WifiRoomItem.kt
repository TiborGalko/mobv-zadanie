package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wifirooms")
data class WifiRoomItem(var ssid: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var posts: MutableList<PostItem> = ArrayList()

    override fun toString(): String {
        return "WifiRoomItem(id=$id, ssid='$ssid', posts=$posts)"
    }
}