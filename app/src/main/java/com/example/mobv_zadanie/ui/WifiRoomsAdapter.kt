package com.example.mobv_zadanie.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobv_zadanie.data.db.model.WifiRoomItem
import com.example.mobv_zadanie.databinding.ListItemWifiRoomBinding

class WifiRoomsAdapter(val clickListener: WifiRoomsListener) : ListAdapter<WifiRoomItem, WifiRoomsAdapter.ViewHolder>(WifiRoomsDiffCallback()) {

    class ViewHolder private constructor(val binding: ListItemWifiRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        // Calls bindings from BindingUtils
        fun bind(item: WifiRoomItem, clickListener: WifiRoomsListener) {
            binding.wifiRoom = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemWifiRoomBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) // getItem gets WifiRoomItem from ListAdapter
        holder.bind(item, clickListener)
    }
}

// Used for comparing differences between items so we don't have to reload full list
class WifiRoomsDiffCallback() : DiffUtil.ItemCallback<WifiRoomItem>() {
    override fun areItemsTheSame(oldItem: WifiRoomItem, newItem: WifiRoomItem): Boolean {
        return oldItem.ssid == newItem.ssid
    }

    override fun areContentsTheSame(oldItem: WifiRoomItem, newItem: WifiRoomItem): Boolean {
        return oldItem == newItem // Calls WifiRoomItem.equals
    }

}

class WifiRoomsListener(val clickListener: (roomSSID: String) -> Unit) {
    fun onClick(wifiRoom: WifiRoomItem) = clickListener(wifiRoom.ssid)
}