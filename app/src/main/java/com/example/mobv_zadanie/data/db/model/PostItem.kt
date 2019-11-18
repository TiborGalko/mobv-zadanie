package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
class PostItem(@PrimaryKey val id: String) {
}