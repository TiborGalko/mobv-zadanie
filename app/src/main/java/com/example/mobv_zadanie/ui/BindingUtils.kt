package com.example.mobv_zadanie.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.db.model.UserItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem
import java.util.*

// These methods are used to bind layout items in recycler view. One BindingAdapter for one component
@BindingAdapter("roomName")
fun TextView.setRoomNameString(item: WifiRoomItem) {
    text = item.ssid
}

@BindingAdapter("contactName")
fun TextView.setContactNameString(item: UserItem) {
    text = item.name
}