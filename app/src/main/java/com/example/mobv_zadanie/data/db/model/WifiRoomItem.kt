package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wifiroom")
class WifiRoomItem(@PrimaryKey val ssid: String) {
    override fun toString(): String {
        return "WifiRoomItem(ssid='$ssid')"
    }
}