package com.example.mobv_zadanie.ui


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.Injection
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.databinding.FragmentWifiRoomsBinding
import com.example.mobv_zadanie.ui.adapters.WifiRoomsAdapter
import com.example.mobv_zadanie.ui.adapters.WifiRoomsListener
import com.example.mobv_zadanie.ui.viewModels.WifiRoomsViewModel

/**
 * A simple [Fragment] subclass.
 */
class WifiRoomsFragment : Fragment() {

    //helper global variable
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var wifiRoomsContext: Context
    }

    private lateinit var wifiRoomsViewModel: WifiRoomsViewModel
    private lateinit var binding: FragmentWifiRoomsBinding

    private val LOCATION = 1 // used for identifying permission call

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Wifi Rooms"
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_wifi_rooms, container, false
        )

        // Setting navController for NavigationDrawer
        val navController = this.findNavController()
        NavigationUI.setupWithNavController(binding.navView, navController)

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this

        binding.toContactsBtn.setOnClickListener {
            this.findNavController().navigate(
                WifiRoomsFragmentDirections.actionWifiRoomsFragmentToContactsFragment()
            )
        }

        // get ViewModel from dependency Injection. Injection class creates new ViewModel from ViewModelFactory
        wifiRoomsViewModel = ViewModelProvider(this, Injection.provideViewModelFactory(context!!))
            .get(WifiRoomsViewModel::class.java)

        // set ViewModel
        binding.model = wifiRoomsViewModel

        // attach recycler view adapter
        val adapter =
            WifiRoomsAdapter(WifiRoomsListener { wifiRoomSSID ->
                wifiRoomsViewModel.onWifiRoomItemClicked(wifiRoomSSID)
            })
        binding.wifiRoomsList.adapter = adapter
        wifiRoomsViewModel.wifiRooms.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        wifiRoomsViewModel.navigateToWifiRoom.observe(this, Observer { wifiRoom ->
            wifiRoom?.let {
                this.findNavController().navigate(
                    WifiRoomsFragmentDirections.actionWifiRoomsFragmentToPostsFragment(wifiRoom))
                wifiRoomsViewModel.onWifiRoomNavigated() // reset state
            }
        })

        // This is because you need ACCESS_FINE_LOCATION permissions to get ssid from wifiManager
        // https://stackoverflow.com/questions/49977395/on-oreo-8-1-0-not-getting-the-correct-wifi-ssid-its-showing-unknown-ssid-t?noredirect=1
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Request permission from user
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),LOCATION)
        } else {//Permission already granted
            saveCurrentWifiRoom()
        }
        wifiRoomsViewModel.saveCurrentWifiRoom("XsTDHS3C2YneVmEW5Ry7")
        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == LOCATION) {
            //User allowed the location and you can read it now
            println("Allowed")
            saveCurrentWifiRoom()
        }
    }

    private fun saveCurrentWifiRoom() {
        val wifiManager = context!!.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
            val ssid = wifiInfo.ssid //Here you can access your SSID
            val bssid = wifiInfo.bssid
            println(bssid)
            println(ssid)
            if (ssid == "<unknown ssid>" || ssid == "") {
                wifiRoomsViewModel.saveCurrentWifiRoom(bssid)
            } else {
                wifiRoomsViewModel.saveCurrentWifiRoom(prepareSsid(ssid))
            }
        }
    }

    private fun prepareSsid(ssid: String) : String {
        var safeSSID = ssid
        if (ssid.startsWith("\"") and ssid.endsWith("\"")) {
            safeSSID = ssid.substring(1, ssid.length - 1)
        }
        val regex = Regex("[^a-zA-Z0-9-_.~%]")
        safeSSID = regex.replace(safeSSID, "_")
        return safeSSID
    }

    //after fragment onCreateView method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //pass context to use for SharedPrefWorker
        wifiRoomsContext = view.context

        wifiRoomsViewModel.listWifiRooms(wifiRoomsContext)
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
            wifiRoomsViewModel.logout(wifiRoomsContext)

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