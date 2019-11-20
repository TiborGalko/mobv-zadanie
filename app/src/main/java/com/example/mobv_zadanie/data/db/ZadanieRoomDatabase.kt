package com.example.mobv_zadanie.data.db

import android.content.Context
import android.os.Message
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mobv_zadanie.data.db.model.MessageItem
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.data.db.model.UserItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem

// https://codelabs.developers.google.com/codelabs/kotlin-android-training-room-database/#5
@Database(
    entities = [PostItem::class, UserItem::class, WifiRoomItem::class, MessageItem::class], // This is where entities are added
    version = 1,
    exportSchema = false
)
@TypeConverters(com.example.mobv_zadanie.data.db.TypeConverters::class)
abstract class ZadanieRoomDatabase : RoomDatabase() {

    abstract fun appDao(): ZadanieRoomDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: ZadanieRoomDatabase? = null

        fun getInstance(context: Context): ZadanieRoomDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ZadanieRoomDatabase::class.java, "mobv-zadanie.db" // Name of database is here
            ).fallbackToDestructiveMigration()
                .build()
    }

}