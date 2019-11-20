package com.example.mobv_zadanie.data.util

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.mobv_zadanie.data.*
import com.example.mobv_zadanie.data.db.*

object Injection {
    private fun provideCache(context: Context): LocalCache {
        val database = ZadanieRoomDatabase.getInstance(context) // get instance of database. It is created if null
        return LocalCache(database.appDao()) // use database to create LocalCache
    }

    fun provideDataRepository(context: Context): DataRepository {
        return DataRepository.getInstance(provideCache(context))
    }

    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(
            provideDataRepository(
                context
            )
        )
    }
}