package com.example.mobv_zadanie.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mobv_zadanie.data.db.model.UserItem

@Dao
interface ZadanieRoomDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(userItem: UserItem)

    @Query("SELECT * FROM users")
    fun getLoaded(): LiveData<List<UserItem>>
}