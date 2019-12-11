package com.example.mobv_zadanie.ui.adapters

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


class ChatMessagesAdapter() : ListAdapter<MessageItem, ChatMessagesAdapter.ViewHolder>(
    ChatDiffCallback()
){

    class ViewHolder private constructor(val binding: ListMessagesBinding) : RecyclerView.ViewHolder(binding.root) {
        val gif = "gif:"
        // Calls bindings from BindingUtils
        fun bind(item: MessageItem) {
            binding.message = item
            binding.executePendingBindings()
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
        val item = getItem(position) // getItem gets MessageItem from ListAdapter
        holder.bind(item)
    }
}

class ChatDiffCallback() : DiffUtil.ItemCallback<MessageItem>() {
    override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
        return oldItem == newItem
    }

}

