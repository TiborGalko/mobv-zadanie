package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date


@Entity(tableName = "posts")
data class PostItem(var uid: String, var roomId: String, var message: String, var name:String, var time: Date) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0



    override fun toString(): String {
        return "PostItem(id=$id, uid=$uid, room=$roomId,  name=$name, time=$time )"
    }
}