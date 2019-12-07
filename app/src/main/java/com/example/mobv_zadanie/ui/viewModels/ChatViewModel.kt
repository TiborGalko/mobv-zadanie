package com.example.mobv_zadanie.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobv_zadanie.data.DataRepository
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: DataRepository) : ViewModel() {

    fun sendMessage(context: Context, room:String, message:String){
        viewModelScope.launch { repository.postMessage(context,room,message) }
    }
}