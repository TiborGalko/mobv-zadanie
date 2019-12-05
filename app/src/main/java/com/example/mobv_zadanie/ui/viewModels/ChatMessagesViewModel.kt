package com.example.mobv_zadanie.ui.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.data.db.model.MessageItem
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.databinding.FragmentChatMessagesBinding
import kotlinx.coroutines.launch

class ChatMessagesViewModel (private val repository: DataRepository) : ViewModel() {
    private val _navigateToPost = MutableLiveData<String>()

    val chat : LiveData<List<MessageItem>>
        get() = repository.getChatSorted()


    fun chatList(context: Context, uid:String) {
        viewModelScope.launch { repository.chatList(context,uid)}
    }

    fun sendMessage(context: Context, id:String, message:String){
        viewModelScope.launch { repository.postChatMessage(context,id,message) }
    }

}