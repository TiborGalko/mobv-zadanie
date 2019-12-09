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
        get() = repository.getWifiRoomsSorted() //TODO toto treba tiez menit asi

    fun listWifiRooms(context: Context) {
        viewModelScope.launch { repository.wifiRoomList(context) }
    }



    fun saveCurrentWifiRoom(ssid: String, bssid: String) {
        val date = java.util.Date()
        if (ssid == "unknown ssid" || ssid == "") {
            viewModelScope.launch { repository.insertWifiRoom(bssid, Timestamp(date.time)) }
        } else {
            viewModelScope.launch { repository.insertWifiRoom(ssid, Timestamp(date.time)) }
        }
        println("Inserted new wifi room") //TODO remove
    }

    fun logout(context: Context) {
        viewModelScope.launch { repository.logout(context) }
    }

    fun onWifiRoomItemClicked(ssid: String) {
        _navigateToWifiRoom.value = ssid

    }

    // Called after navigation is finished to reset navigation state
    fun onWifiRoomNavigated() {
        _navigateToWifiRoom.value = null
    }
}