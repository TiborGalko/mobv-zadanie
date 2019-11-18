package com.example.mobv_zadanie.data

import androidx.lifecycle.LiveData
import com.example.mobv_zadanie.data.db.LocalCache
import com.example.mobv_zadanie.data.db.model.UserItem

class DataRepository private constructor(private val cache: LocalCache) {

    companion object {
        const val TAG = "DataRepository"
        @Volatile
        private var INSTANCE: DataRepository ?= null

        fun getInstance(cache: LocalCache): DataRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataRepository(cache).also { INSTANCE = it }
            }
    }

    fun getLoaded() : LiveData<List<UserItem>> = cache.getLoaded()
}