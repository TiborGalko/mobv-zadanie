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
import androidx.navigation.ui.NavigationUI

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.Injection
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.databinding.FragmentChatBinding
import com.example.mobv_zadanie.ui.viewModels.ChatViewModel
import com.giphy.sdk.core.models.enums.RenditionType
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.GiphyCoreUI
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.themes.LightTheme
import com.giphy.sdk.ui.views.GPHMediaView
import com.giphy.sdk.ui.views.GiphyDialogFragment
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

        // Setting navController for NavigationDrawer
        val navController = this.findNavController()
        NavigationUI.setupWithNavController(binding.navView, navController)

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
            chatViewModel.sendMessage(room,message, ChatContext)
            hideKeyboard()
            this.findNavController().navigate(
                ChatFragmentDirections.actionChatFragmentToPostsFragment(room)
            )
        }

        post_gif.setOnClickListener {
            GiphyCoreUI.configure(context!!, "qS2O0jkAE54Wem1ly9p9B3SOFrC5ibU3")
            GiphyDialogFragment.newInstance().show(fragmentManager!!,"giphy_dialog")
            var settings = GPHSettings(gridType = GridType.waterfall, theme = LightTheme, dimBackground = true)
            settings.gridType = GridType.waterfall
            settings.mediaTypeConfig = arrayOf(GPHContentType.gif)
            settings.renditionType = RenditionType.fixedWidth
            settings.renditionType = RenditionType.fixedHeight
            settings.confirmationRenditionType = RenditionType.original

            val gifsDialog = GiphyDialogFragment.newInstance(settings)
            gifsDialog.show(fragmentManager!!, "gifs_dialog")

            gifsDialog.gifSelectionListener = object: GiphyDialogFragment.GifSelectionListener {
                override fun onGifSelected(media: Media) {
                    val mediaView = GPHMediaView(context!!)
                    mediaView.setMedia(media, RenditionType.original)
                    val message = ("gif:" + media.id)
                    chatViewModel.sendMessage(room,message, ChatContext)
                    hideKeyboard()
                    findNavController().navigate(
                        ChatFragmentDirections.actionChatFragmentToPostsFragment(room)
                    )
                }
                override fun onDismissed() {
                    //Your user dismissed the dialog without selecting a GIF
                }
            }

        }

    }
    private fun hideKeyboard(){
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

}
