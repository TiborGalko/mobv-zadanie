package com.example.mobv_zadanie.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv_zadanie.data.db.model.ContactItem
import com.example.mobv_zadanie.databinding.ListItemContactsBinding

class ContactsAdapter(val clickListener: ContactsListener) : ListAdapter<ContactItem, ContactsAdapter.ViewHolder>(ContactsDiffCallback()) {

    class ViewHolder private constructor(val binding: ListItemContactsBinding) : RecyclerView.ViewHolder(binding.root) {

        // Calls bindings from BindingUtils
        fun bind(item: ContactItem, clickListener: ContactsListener) {
            binding.contact = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemContactsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        val item = getItem(position) // getItem gets ContactsItem from ListAdapter
        holder.bind(item, clickListener)
    }
}

class ContactsDiffCallback() : DiffUtil.ItemCallback<ContactItem>() {
    override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem == newItem
    }
}


class ContactsListener(val clickListener: (contactName: String) -> Unit) {
    fun onClick(contact: ContactItem) = clickListener(contact.name)
}