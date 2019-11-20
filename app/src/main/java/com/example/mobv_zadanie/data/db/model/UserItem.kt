package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob
import java.sql.Date

@Entity(tableName = "users")
data class UserItem(var name: String?)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var authToken: String? = null
    var refreshToken: String? = null
    var profilePic: Blob? = null  // TODO not sure if this is the right way
    var lastOnline: Date? = null
    var wifiRooms: MutableList<WifiRoomItem> = ArrayList()
    var contacts: MutableList<UserItem> = ArrayList()
    var messages: MutableList<MessageItem> = ArrayList()

    override fun toString(): String {
        return "UserItem(id=$id, name=$name, authToken=$authToken, refreshToken=$refreshToken, profilePic=$profilePic, lastOnline=$lastOnline, wifiRooms=$wifiRooms, contacts=$contacts, messages=$messages)"
    }
}