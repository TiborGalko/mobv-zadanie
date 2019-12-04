package com.example.mobv_zadanie.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.ui.PostAdapter.ViewHolder
import com.example.mobv_zadanie.databinding.ListItemPostsBinding

class PostAdapter() : ListAdapter<PostItem, ViewHolder>(PostsDiffCallback()){

    class ViewHolder private constructor(val binding: ListItemPostsBinding) : RecyclerView.ViewHolder(binding.root) {
        // Calls bindings from BindingUtils
        fun bind(item: PostItem) {
            //binding.post = item
            binding.time.text = item.time.toString()
            binding.message.text = item.message
            binding.name.text = item.name
            binding.executePendingBindings()
        }



        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemPostsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) // getItem gets WifiRoomItem from ListAdapter
        holder.bind(item)
    }

}




class PostsDiffCallback() : DiffUtil.ItemCallback<PostItem>() {
    override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
        return oldItem == newItem // Calls WifiRoomItem.equals
    }

}