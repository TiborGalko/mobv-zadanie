package com.example.mobv_zadanie.ui

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mobv_zadanie.data.db.model.ContactItem
import com.example.mobv_zadanie.data.db.model.MessageItem
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
    val message = item.message
    if (message.contains("gif:")) {
        visibility = View.INVISIBLE
    } else {
        text = item.message
        visibility = View.VISIBLE
    }
}

// Clear needs to be here... Otherwise gifs will be shown on other items and bad things will happen...
// https://stackoverflow.com/questions/32706246/recyclerview-adapter-and-glide-same-image-every-4-5-rows
@BindingAdapter("postGifImage")
fun bindPostGifImage(imgView: ImageView, postMessage: String?) {
    if(postMessage != null && postMessage.contains("gif:")){
        val gifId = postMessage.removePrefix("gif:")
        Glide.with(imgView.context)
            .load(Uri.parse("https://media2.giphy.com/media/$gifId/200w.gif"))
            .into(imgView)
        imgView.visibility  = View.VISIBLE
    } else {
        Glide.with(imgView.context).clear(imgView)
        imgView.visibility  = View.INVISIBLE
    }
}

@BindingAdapter("messageName")
fun TextView.setNameString(item: MessageItem) {
    text = item.uid_name
}

@BindingAdapter("messageMyMessage")
fun TextView.setMyMessageString(item: MessageItem) {
    val message = item.message
    if (message.contains("gif:")) {
        visibility = View.INVISIBLE
    } else {
        text = item.message
        visibility = View.VISIBLE
    }
}

// Same thing as above
// https://stackoverflow.com/questions/32706246/recyclerview-adapter-and-glide-same-image-every-4-5-rows
@BindingAdapter("messageGifImage")
fun bindMessageGifImage(imgView: ImageView, messageMessage: String?) {
    if(messageMessage != null && messageMessage.contains("gif:")){
        val gifId = messageMessage.removePrefix("gif:")
        Glide.with(imgView.context)
            .load(Uri.parse("https://media2.giphy.com/media/$gifId/200w.gif"))
            .into(imgView)
        imgView.visibility  = View.VISIBLE
    } else {
        Glide.with(imgView.context).clear(imgView)
        imgView.visibility  = View.INVISIBLE
    }
}

