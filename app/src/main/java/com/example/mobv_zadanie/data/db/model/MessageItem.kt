package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageItem(
    @PrimaryKey val id: Int,
    val sender: UserItem,
    val receiver: UserItem,
    val text: String
    )
{
    override fun toString(): String {
        return "MessageItem(id=$id, sender=$sender, receiver=$receiver, text='$text')"
    }
}