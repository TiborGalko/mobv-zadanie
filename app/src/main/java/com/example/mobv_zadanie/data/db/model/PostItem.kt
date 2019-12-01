package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostItem(var text: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var poster: String? = null


    override fun toString(): String {
        return "PostItem(id=$id, poster=$poster, text='$text')"
    }
}