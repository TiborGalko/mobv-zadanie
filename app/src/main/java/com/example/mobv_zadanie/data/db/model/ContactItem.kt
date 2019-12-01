package com.example.mobv_zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class representing contacts
 * */
@Entity(tableName = "contacts")
data class ContactItem(val name: String, @PrimaryKey val id: String) {
    override fun toString(): String {
        return "ContactItem(name='$name', id=$id)"
    }
}