package com.example.mobv_zadanie.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.Injection
import com.example.mobv_zadanie.databinding.FragmentWifiRoomsBinding
import com.example.mobv_zadanie.ui.viewModels.WifiRoomsViewModel

/**
 * A simple [Fragment] subclass.
 */
class WifiRoomsFragment : Fragment() {

    private lateinit var wifiRoomsViewModel: WifiRoomsViewModel
    private lateinit var binding: FragmentWifiRoomsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_wifi_rooms, container, false
        )
        binding.lifecycleOwner = this
        wifiRoomsViewModel = ViewModelProvider(this, Injection.provideViewModelFactory(context!!))
            .get(WifiRoomsViewModel::class.java)

        binding.model = wifiRoomsViewModel

        return binding.root
    }
}
