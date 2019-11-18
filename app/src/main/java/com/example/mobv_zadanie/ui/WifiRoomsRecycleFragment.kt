package com.example.mobv_zadanie.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mobv_zadanie.R

/**
 * A simple [Fragment] subclass.
 */
class WifiRoomsRecycleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wifi_rooms_recycle, container, false)
    }


}
