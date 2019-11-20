package com.example.mobv_zadanie.data.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.ui.viewModels.WifiRoomsViewModel

class ViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WifiRoomsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WifiRoomsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(WifiRoomsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WifiRoomsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(WifiRoomsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WifiRoomsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(WifiRoomsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WifiRoomsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(WifiRoomsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WifiRoomsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(WifiRoomsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WifiRoomsViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}