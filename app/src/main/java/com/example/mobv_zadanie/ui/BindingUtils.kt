package com.example.mobv_zadanie.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mobv_zadanie.data.db.model.ContactItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem

// These methods are used to bind layout items in recycler view. One BindingAdapter for one component
@BindingAdapter("roomName")
fun TextView.setRoomNameString(item: WifiRoomItem) {
    text = item.ssid
}

@BindingAdapter("contactName")
fun TextView.setContactNameString(item: ContactItem) {
    text = item.name
}