package com.example.mobv_zadanie.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: DataRepository) : ViewModel() {

    fun sendMessage(room:String, message:String,context: Context){
        viewModelScope.launch { repository.postMessage(room,message,context) }

        println("Posting message $room $message")
        val name = SharedPrefWorker.getString(context, "name", "").toString()
        if (name != "") {
            viewModelScope.launch { repository.postFcmMessage(room, message,
                "Room: $room\nuser $name"
            ) }
        }
    }

    fun logout(context: Context) {
        viewModelScope.launch { repository.logout(context) }
    }
}