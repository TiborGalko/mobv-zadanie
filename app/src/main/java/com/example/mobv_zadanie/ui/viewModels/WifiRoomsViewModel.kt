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

    val wifiRooms : LiveData<List<WifiRoomItem>>
        get() = repository.getWifiRooms()

    val text: LiveData<String> = Transformations.map(wifiRooms) { it.toString() }

    fun insertWord() {
        input.value?.let {
            if (it.isNotEmpty()) {
                val wifiRoom = WifiRoomItem("Testovacia Roomka")

                val postItem = PostItem(it)

                val userItem = UserItem("Testovaci User")
                postItem.poster = userItem

                wifiRoom.posts.add(postItem)
                viewModelScope.launch { repository.insertWifiRoom(wifiRoomItem = wifiRoom) }
            }
        }
    }
}