package com.example.mobv_zadanie.data.util

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.mobv_zadanie.data.*
import com.example.mobv_zadanie.data.db.*

object Injection {
    private fun provideCache(context: Context): LocalCache {
        val database = ZadanieRoomDatabase.getInstance(context)
        return LocalCache(database.appDao())
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