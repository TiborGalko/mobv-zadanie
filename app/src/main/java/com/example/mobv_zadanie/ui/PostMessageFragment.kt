package com.example.mobv_zadanie.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.example.mobv_zadanie.R

/**
 * A simple [Fragment] subclass.
 */
class PostMessageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title = "Pridať príspevok"
        return inflater.inflate(R.layout.fragment_post_message, container, false)
    }


}
