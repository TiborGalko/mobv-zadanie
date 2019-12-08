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

    var uidcontact=""

    val chat : LiveData<List<MessageItem>>
        get() = repository.getcontactSorted(uidcontact)


    fun fillvar(uid: String) {
       uidcontact = uid
    }


    fun chatList(uid:String,context: Context) {
        viewModelScope.launch { repository.chatList(uid,context)}
    }

    fun sendMessage(id:String, message:String,context: Context){
        viewModelScope.launch { repository.postChatMessage(id,message,context) }
    }

    fun logout(context: Context) {
        viewModelScope.launch { repository.logout(context) }
    }



}