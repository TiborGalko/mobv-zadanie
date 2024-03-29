package com.example.mobv_zadanie.data.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.ui.viewModels.*

class ViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WifiRoomsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WifiRoomsViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostsViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(ChatMessagesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatMessagesViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}