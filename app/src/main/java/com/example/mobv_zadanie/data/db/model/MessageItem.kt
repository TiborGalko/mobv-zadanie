package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "messages", indices = [Index(value = ["uid","contact","message","time", "uid_name", "contact_name", "uid_fid", "contact_fid"], unique = true)])
data class MessageItem(var uid: String, var contact:String, var message:String, val time: Timestamp, var uid_name:String, var contact_name:String, var uid_fid:String, var contact_fid:String)
{

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "MessageItem(id=$id, uid:$uid, contact:$contact, message:$message, time:$time, uid_name:$uid_name, contact_name:$contact_name, uid_fid:$uid_fid, cintact_fid:$contact_fid)"
    }
}