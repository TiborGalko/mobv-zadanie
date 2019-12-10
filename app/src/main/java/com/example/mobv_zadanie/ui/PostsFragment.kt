package com.example.mobv_zadanie.ui


import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.SupplicantState
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.Injection
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.databinding.FragmentPostsBinding
import com.example.mobv_zadanie.ui.viewModels.PostsViewModel
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.fragment_posts.text

/**
 * A simple [Fragment] subclass.
 */
class PostsFragment : Fragment() {

    val args:PostsFragmentArgs by navArgs()

    //helper global variable
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var roomPostContext: Context
    }


    private lateinit var postsViewModel: PostsViewModel
    private lateinit var binding: FragmentPostsBinding

    private val LOCATION = 1 // used for identifying permission call

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = args.wifiRoomSSID
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_posts, container, false
        )
        binding.lifecycleOwner = this
        binding.addButton.setOnClickListener {
            this.findNavController().navigate(
                PostsFragmentDirections.actionPostsFragmentToChatFragment()
            )
        }

        postsViewModel = ViewModelProvider(this, Injection.provideViewModelFactory(context!!))
            .get(PostsViewModel::class.java)
        postsViewModel.fillvar(args.wifiRoomSSID)
        binding.model = postsViewModel
        val adapter = PostAdapter(context!!, args.wifiRoomSSID, PostsListener { contactId ->
            postsViewModel.onPostItemClicked(contactId)
        })
        binding.posts.adapter = adapter
        binding.posts.removeAllViewsInLayout()
        //println(postsViewModel.roomPosts.value)
        postsViewModel.roomPosts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        postsViewModel.navigateToPosts.observe(this, Observer { contactId ->
            contactId?.let {
                this.findNavController().navigate(
                    PostsFragmentDirections.actionPostsFragmentToChatmessagesFragment(contactId))
                postsViewModel.onPostItemNavigated() // reset state
            }
        })

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
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    //inflate menu - need menu because user should have ability to uncheck auto login
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.custom_menu, menu)
        //set value of user settings
        menu.getItem(0).isChecked = SharedPrefWorker.getBoolean(roomPostContext, "autoLoginChecked")!!
        super.onCreateOptionsMenu(menu, inflater)
    }

    //select actions for menu
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_autoLogin -> {

            item.isChecked = !item.isChecked
            SharedPrefWorker.saveBoolean(roomPostContext, "autoLoginChecked", item.isChecked)
            true
        }
        R.id.action_logout -> {
            postsViewModel.logout(roomPostContext)

            findNavController().navigate(R.id.loginFragment)
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomPostContext = view.context
        binding.posts.removeAllViewsInLayout()
        SharedPrefWorker.saveString(roomPostContext, "room",args.wifiRoomSSID )
        val wifiManager = context!!.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        if( wifiInfo.networkId == -1){
            if(args.wifiRoomSSID=="XsTDHS3C2YneVmEW5Ry7"){
                text.visibility = View.INVISIBLE
            }else{
                add_button.visibility = View.INVISIBLE
            }
        }else{
            if (wifiInfo.supplicantState == SupplicantState.COMPLETED) {
                val ssid = wifiInfo.ssid//Here you can access your SSID
                val bssid = wifiInfo.bssid
                if (ssid == "<unknown ssid>" || ssid == "") {
                    if (bssid==args.wifiRoomSSID){
                        text.visibility = View.INVISIBLE
                    }else{
                        add_button.visibility = View.INVISIBLE
                    }
                } else if(args.wifiRoomSSID=="XsTDHS3C2YneVmEW5Ry7"){
                    if(args.wifiRoomSSID=="XsTDHS3C2YneVmEW5Ry7"){
                        text.visibility = View.INVISIBLE
                    }else{
                        add_button.visibility = View.INVISIBLE
                    }
                } else {
                    if (ssid.substring(1, ssid.length-1) == args.wifiRoomSSID){
                        text.visibility = View.INVISIBLE
                    }else{
                        add_button.visibility = View.INVISIBLE
                    }
                }
            }
        }
        postsViewModel.listPosts(view.context)
    }



    override fun onResume() {
        super.onResume()
        binding.posts.removeAllViewsInLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.posts.removeAllViewsInLayout()
    }


}
