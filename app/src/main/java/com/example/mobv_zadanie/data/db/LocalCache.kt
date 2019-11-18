package com.example.mobv_zadanie.data.db

import androidx.lifecycle.LiveData
import com.example.mobv_zadanie.data.db.model.UserItem

class LocalCache(private val dao: ZadanieRoomDatabaseDao) {

    fun getLoaded() : LiveData<List<UserItem>> {
        return dao.getLoaded()
    }

}