package com.example.mobv_zadanie.ui.viewModels

import androidx.lifecycle.*
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.data.db.model.UserItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem
import kotlinx.coroutines.launch


// https://codelabs.developers.google.com/codelabs/kotlin-android-training-coroutines-and-room/#3
class WifiRoomsViewModel(private val repository: DataRepository) : ViewModel() {

    val input: MutableLiveData<String> = MutableLiveData()

    // Used to trigger navigation saves navigation state
    private val _navigateToWifiRoom = MutableLiveData<String>()
    // Public gettable val variable to use with mutable live data private variable
    val navigateToWifiRoom
        get() = _navigateToWifiRoom


    val wifiRooms : LiveData<List<WifiRoomItem>>
        get() = repository.getWifiRoomsSorted()

    fun insertWord() {
        input.value?.let {
            if (it.isNotEmpty()) {
                val wifiRoom = WifiRoomItem(it)

                val postItem = PostItem("Test post")

                val userItem = UserItem("Testovaci User")
                postItem.poster = userItem

                wifiRoom.posts.add(postItem)
                viewModelScope.launch { repository.insertWifiRoom(wifiRoomItem = wifiRoom) }
                println("Inserted new wifi room")
            }
        }
        input.value = ""
    }

    fun onWifiRoomItemClicked(ssid: String) {
        _navigateToWifiRoom.value = ssid

    }

    // Called after navigation is finished to reset navigation state
    fun onWifiRoomNavigated() {
        _navigateToWifiRoom.value = null
    }
}