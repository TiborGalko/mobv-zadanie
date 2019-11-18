package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserItem(@PrimaryKey val name: String) {
    override fun toString(): String {
        return "UserItem(name='$name')"
    }
}