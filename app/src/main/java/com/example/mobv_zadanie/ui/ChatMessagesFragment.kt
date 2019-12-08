package com.example.mobv_zadanie.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.Injection
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.databinding.FragmentChatMessagesBinding
import com.example.mobv_zadanie.ui.viewModels.ChatMessagesViewModel
import kotlinx.android.synthetic.main.fragment_chat_messages.*
import kotlinx.android.synthetic.main.list_messages.*
import kotlinx.android.synthetic.main.list_messages.message

class ChatMessagesFragment:Fragment() {

    val args:ChatMessagesFragmentArgs by navArgs()

    //helper global variable
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var chatContext: Context
    }

    private lateinit var chatViewModel: ChatMessagesViewModel
    private lateinit var binding: FragmentChatMessagesBinding

    private val LOCATION = 1 // used for identifying permission call

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //(activity as AppCompatActivity).supportActionBar?.title = args.wifiRoomSSID
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_chat_messages, container, false
        )
        binding.lifecycleOwner = this


        chatViewModel = ViewModelProvider(this, Injection.provideViewModelFactory(context!!))
            .get(ChatMessagesViewModel::class.java)
        binding.model = chatViewModel
        val adapter = ChatAdapter(args.contactId)
        binding.recyclerView.adapter = adapter
        Log.i("FRAG_LOG" , args.contactId)
        binding.recyclerView.removeAllViewsInLayout()
        //println(postsViewModel.roomPosts.value)
        chatViewModel.chat.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
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
        menu.getItem(0).isChecked = SharedPrefWorker.getBoolean(chatContext, "autoLoginChecked")!!
        super.onCreateOptionsMenu(menu, inflater)
    }

    //select actions for menu
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_autoLogin -> {

            item.isChecked = !item.isChecked
            SharedPrefWorker.saveBoolean(chatContext, "autoLoginChecked", item.isChecked)
            true
        }
        R.id.action_logout -> {
            chatViewModel.logout(chatContext)
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
        chatContext = view.context
        chatViewModel.chatList(args.contactId )
        send.setOnClickListener{
            val message = message_text.text.toString()
            chatViewModel.sendMessage(args.contactId,message)
            hideKeyboard()
        }
    }
    private fun hideKeyboard(){
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

}