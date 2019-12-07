package com.example.mobv_zadanie.ui.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.data.db.model.WifiRoomItem
import kotlinx.coroutines.launch
import java.sql.Timestamp


// https://codelabs.developers.google.com/codelabs/kotlin-android-training-coroutines-and-room/#3
class WifiRoomsViewModel(private val repository: DataRepository) : ViewModel() {

    // Used to trigger navigation saves navigation state
    private val _navigateToWifiRoom = MutableLiveData<String>()
    // Public gettable val variable to use with mutable live data private variable
    val navigateToWifiRoom
        get() = _navigateToWifiRoom

    val wifiRooms : LiveData<List<WifiRoomItem>>
        get() = repository.getWifiRoomsSorted()

    fun listWifiRooms(context: Context) {
        viewModelScope.launch { repository.wifiRoomList(context) }
    }



    fun saveCurrentWifiRoom(ssid: String, bssid: String) {
        val date = java.util.Date()
        if (ssid == "<unknown ssid>" || ssid == "") {
            val wifiRoom = WifiRoomItem(bssid, Timestamp(date.time))
            viewModelScope.launch { repository.insertWifiRoom(wifiRoomItem = wifiRoom) }
        } else {
            val wifiRoom = WifiRoomItem(ssid, Timestamp(date.time))
            viewModelScope.launch { repository.insertWifiRoom(wifiRoomItem = wifiRoom) }
        }
        println("Inserted new wifi room") //TODO remove
    }


    fun onWifiRoomItemClicked(ssid: String) {
        _navigateToWifiRoom.value = ssid

    }

    // Called after navigation is finished to reset navigation state
    fun onWifiRoomNavigated() {
        _navigateToWifiRoom.value = null
    }
}