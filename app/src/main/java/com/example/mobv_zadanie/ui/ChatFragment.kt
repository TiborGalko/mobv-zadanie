package com.example.mobv_zadanie.ui


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.Injection
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.databinding.FragmentChatBinding
import com.example.mobv_zadanie.ui.viewModels.ChatViewModel
import kotlinx.android.synthetic.main.fragment_chat.*

/**
 * A simple [Fragment] subclass.
 */
class ChatFragment : Fragment() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var ChatContext: Context
    }

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_chat, container, false
        )
        binding.lifecycleOwner = this

        chatViewModel = ViewModelProvider(this, Injection.provideViewModelFactory(context!!))
            .get(ChatViewModel::class.java)

        binding.model = chatViewModel
        return binding.root
    }
    //after fragment onCreateView method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //pass context to use for SharedPrefWorker
        ChatContext = view.context
        val room = SharedPrefWorker.getString(ChatContext, "room", "public").toString()
        post.setOnClickListener{
            val message = text.text.toString()
            chatViewModel.sendMessage(ChatContext,room,message)
            hideKeyboard()
            this.findNavController().navigate(
                ChatFragmentDirections.actionChatFragmentToPostsFragment(room)
            )
        }

    }
    private fun hideKeyboard(){
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

}
