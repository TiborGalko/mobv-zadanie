package com.example.mobv_zadanie.ui.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.data.db.model.WifiRoomItem
import com.google.firebase.messaging.FirebaseMessaging
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

    fun postFirebaseId(fid: String) {
        viewModelScope.launch { repository.postFirebaseId(fid) }
    }

    fun saveCurrentWifiRoom(roomId: String) {
        val date = java.util.Date()
        viewModelScope.launch { repository.insertWifiRoom(roomId, Timestamp(date.time)) }

        // Subscribe to room fcm topic, it is created if it doesn't exist
        FirebaseMessaging.getInstance().subscribeToTopic(roomId)
            .addOnCompleteListener { task ->
                var msg = "Now you are subscribed to $roomId fcm topic."
                if (!task.isSuccessful) {
                    msg = "Topic subscription failed."
                }
                Log.d("TAG_API", msg)
                println(msg)
            }
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