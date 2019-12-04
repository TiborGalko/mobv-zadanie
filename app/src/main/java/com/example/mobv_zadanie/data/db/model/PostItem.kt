package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "posts")
data class PostItem(var uid: String, var roomdid:String, var message:String, val time: Timestamp, var name:String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "WifiRoomItem(id='$id', uid='$uid', roomid='$roomdid', message='$message', time='$time', name='$name')"
    }
}