package com.example.mobv_zadanie.ui.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.data.db.model.MessageItem
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import kotlinx.coroutines.launch

class ChatMessagesViewModel (private val repository: DataRepository) : ViewModel() {
    var uidcontact=""
    var fidcontact=""

    val chat : LiveData<List<MessageItem>>
        get() = repository.getcontactSorted(uidcontact)


    fun fillvar(uid: String) {
        uidcontact = uid
    }

    fun fillFid(fid: String) {
        fidcontact = fid
    }


    fun chatList(uid:String,context: Context) {
        viewModelScope.launch { repository.chatList(uid,context)}
    }

    fun sendMessage(id:String, message:String, context: Context){
        viewModelScope.launch { repository.postChatMessage(id,message,context) }

        println("Posting message $fidcontact $message")
        viewModelScope.launch {
            if (fidcontact != "") {
                val name = SharedPrefWorker.getString(context, "name", "").toString()
                if (name != "") {
                    repository.postFcmMessage(fidcontact, message, name)
                } else {
                    Log.w("FRAG_API","User name is not set.")
                }
            } else {
                Log.w("FRAG_API","Fid of contact is not set.")
            }
        }
    }

    fun logout(context: Context) {
        viewModelScope.launch { repository.logout(context) }
    }
}