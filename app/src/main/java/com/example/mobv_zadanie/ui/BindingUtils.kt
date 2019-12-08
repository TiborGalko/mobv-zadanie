package com.example.mobv_zadanie.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mobv_zadanie.data.db.model.ContactItem
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.data.db.model.WifiRoomItem
import kotlinx.android.synthetic.main.list_item_posts.view.*

// These methods are used to bind layout items in recycler view. One BindingAdapter for one component
@BindingAdapter("roomName")
fun TextView.setRoomNameString(item: WifiRoomItem) {
    text = item.ssid
}

@BindingAdapter("contactName")
fun TextView.setContactNameString(item: ContactItem) {
    text = item.name
}

@BindingAdapter("postAvatar")
fun ImageView.setAvatar(item: PostItem) {
    // TODO nemam ponatia
}

@BindingAdapter("postName")
fun TextView.setNameString(item: PostItem) {
    text = item.name
}

@BindingAdapter("postTime")
fun TextView.setTimeString(item: PostItem) {
    text = item.time.toString()
}

@BindingAdapter("postMessage")
fun TextView.setMessageString(item: PostItem) {
    text = item.message
}