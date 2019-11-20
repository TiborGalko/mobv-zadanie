package com.example.mobv_zadanie.data.db

import androidx.room.TypeConverter
import com.example.mobv_zadanie.data.db.model.MessageItem
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.data.db.model.UserItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem
import com.google.gson.Gson
import java.sql.Blob
import java.sql.Date

class TypeConverters {

    @TypeConverter
    fun listToJson(value: List<String>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        val objects = Gson().fromJson(value, Array<String>::class.java) as Array<String>
        return objects.toList()
    }

    @TypeConverter
    fun userItemListToJson(value: List<UserItem>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToUserItemList(value: String): List<UserItem>? {
        val objects = Gson().fromJson(value, Array<UserItem>::class.java) as Array<UserItem>
        return objects.toList()
    }

    @TypeConverter
    fun postItemListToJson(value: List<PostItem>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToPostItemList(value: String): List<PostItem>? {
        val objects = Gson().fromJson(value, Array<PostItem>::class.java) as Array<PostItem>
        return objects.toList()
    }

    @TypeConverter
    fun wifiRoomItemListToJson(value: List<WifiRoomItem>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToWifiRoomItemList(value: String): List<WifiRoomItem>? {
        val objects = Gson().fromJson(value, Array<WifiRoomItem>::class.java) as Array<WifiRoomItem>
        return objects.toList()
    }

    @TypeConverter
    fun messageItemListToJson(value: List<MessageItem>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToMessageItemList(value: String): List<MessageItem>? {
        val objects = Gson().fromJson(value, Array<MessageItem>::class.java) as Array<MessageItem>
        return objects.toList()
    }

    @TypeConverter
    fun userItemToJson(value: UserItem?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToUserItem(value: String): UserItem? {
        return Gson().fromJson(value, UserItem::class.java) as UserItem
    }

    @TypeConverter
    fun messageItemToJson(value: MessageItem?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToMessageItem(value: String): MessageItem? {
        return Gson().fromJson(value, MessageItem::class.java) as MessageItem
    }

    @TypeConverter
    fun wifiRoomItemToJson(value: WifiRoomItem?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToWifiRoomItem(value: String): WifiRoomItem? {
        return Gson().fromJson(value, WifiRoomItem::class.java) as WifiRoomItem
    }

    @TypeConverter
    fun blobToJson(value: Blob): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToBlob(value: String): Blob {
        return Gson().fromJson(value, Blob::class.java) as Blob
    }

    @TypeConverter
    fun dateToJson(value: Date): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToDate(value: String): Date {
        return Gson().fromJson(value, Date::class.java) as Date
    }

}