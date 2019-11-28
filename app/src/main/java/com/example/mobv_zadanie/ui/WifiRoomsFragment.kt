package com.example.mobv_zadanie.ui


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle

import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.Injection
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.databinding.FragmentWifiRoomsBinding
import com.example.mobv_zadanie.ui.viewModels.WifiRoomsViewModel

/**
 * A simple [Fragment] subclass.
 */
class WifiRoomsFragment : Fragment() {

    //helper global variable
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var wifiRoomsContext: Context
        lateinit var wifiRoomView: View
    }

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

    //after fragment onCreateView method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //pass context to use for SharedPrefWorker
        wifiRoomsContext = view.context
        wifiRoomView = view
    }

    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    //inflate menu - need menu because user should have ability to uncheck auto login
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.custom_menu, menu)
        //set value of user settings
        menu.getItem(0).isChecked = SharedPrefWorker.getBoolean(wifiRoomsContext, "autoLoginChecked")!!
        super.onCreateOptionsMenu(menu, inflater)
    }

    //select actions for menu
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_autoLogin -> {

            item.isChecked = !item.isChecked
            SharedPrefWorker.saveBoolean(wifiRoomsContext, "autoLoginChecked", item.isChecked)
            true
        }
        R.id.action_logout -> {
            findNavController().navigate(R.id.loginFragment)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}
