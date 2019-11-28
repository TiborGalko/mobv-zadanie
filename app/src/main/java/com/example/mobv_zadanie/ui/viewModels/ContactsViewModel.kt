package com.example.mobv_zadanie.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.data.db.model.UserItem
import kotlinx.coroutines.launch

class ContactsViewModel(private val repository: DataRepository) : ViewModel() {

    val input: MutableLiveData<String> = MutableLiveData()

    // Used to trigger navigation saves navigation state
    private val _navigateToContactRoom = MutableLiveData<String>()
    // Public gettable val variable to use with mutable live data private variable
    val navigateToContactRoom
        get() = _navigateToContactRoom


    val contacts : LiveData<List<UserItem>>
        get() = repository.getUsers()

    fun insertContact() {
        input.value?.let {
            if (it.isNotEmpty()) {
                val contact = UserItem(it)
                contact.name = it //TODO

                viewModelScope.launch { repository.insertUser(contact) }
                println("Inserted new contact")
            }
        }
        input.value = ""
    }

    fun onContactClicked(id: String) {
        _navigateToContactRoom.value = id

    }

    // Called after navigation is finished to reset navigation state
    fun onContactNavigated() {
        _navigateToContactRoom.value = null
    }
}