package com.example.mobv_zadanie.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobv_zadanie.data.db.model.MessageItem
import com.example.mobv_zadanie.databinding.ListMessagesBinding


class ChatAdapter(var myuid:String) : ListAdapter<MessageItem, ChatAdapter.ViewHolder>(ChatDiffCallback()){

    class ViewHolder private constructor(val binding: ListMessagesBinding) : RecyclerView.ViewHolder(binding.root) {
        val gif = "gif:"
        // Calls bindings from BindingUtils
        fun bind(item: MessageItem, uid:String) {
            binding.name.text = item.uid_name
            binding.myMessage.text = item.message
            binding.executePendingBindings()

            var message = item.message
            if(item.message.contains("gif:")){
                message = message.removePrefix(gif)
                println(message)
                Glide.with(binding.imageView)
                    .load(Uri.parse("https://media2.giphy.com/media/" + message+ "/200w.gif"))
                    .into(binding.imageView)
                binding.myMessage.visibility = View.INVISIBLE
                binding.imageView.visibility  = View.VISIBLE
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListMessagesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) // getItem gets WifiRoomItem from ListAdapter
        holder.bind(item, myuid)
    }
}

class ChatDiffCallback() : DiffUtil.ItemCallback<MessageItem>() {
    override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem == newItem
    }

}

