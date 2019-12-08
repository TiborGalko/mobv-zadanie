package com.example.mobv_zadanie.ui.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.data.db.model.ContactItem
import kotlinx.coroutines.launch

class ContactsViewModel(private val repository: DataRepository) : ViewModel() {

    // Used to trigger navigation saves navigation state
    private val _navigateToContactRoom = MutableLiveData<String>()
    // Public gettable val variable to use with mutable live data private variable
    val navigateToContactRoom
        get() = _navigateToContactRoom

    val contacts : LiveData<List<ContactItem>>
        get() = repository.getContacts()


    // Get contacts from repository
    fun listContacts() {
        viewModelScope.launch { repository.contactList() }
    }

    fun logout(context: Context) {
        viewModelScope.launch { repository.logout(context) }
    }

    fun onContactClicked(id: String) {
        _navigateToContactRoom.value = id

    }

    // Called after navigation is finished to reset navigation state
    fun onContactNavigated() {
        _navigateToContactRoom.value = null
    }
}