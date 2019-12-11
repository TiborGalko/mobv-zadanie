package com.example.mobv_zadanie.ui.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.databinding.ListItemPostsBinding

class PostAdapter(var context: Context, var room:String, val clickListener: PostsListener) : ListAdapter<PostItem, PostAdapter.ViewHolder>(
    PostsDiffCallback()
){

    class ViewHolder private constructor(val binding: ListItemPostsBinding) : RecyclerView.ViewHolder(binding.root) {
        // Calls bindings from BindingUtils
        fun bind(item: PostItem, clickListener: PostsListener) {
            binding.post = item
            binding.clickListener = clickListener
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
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

}


class PostsDiffCallback : DiffUtil.ItemCallback<PostItem>() {
    override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
        return oldItem == newItem
    }

}

class PostsListener(val clickListener: (contact_uid: String) -> Unit) {
    fun onClick(post: PostItem) = clickListener(post.uid)
}