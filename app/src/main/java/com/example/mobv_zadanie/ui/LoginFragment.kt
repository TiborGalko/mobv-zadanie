package com.example.mobv_zadanie.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import com.example.mobv_zadanie.R
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login = view?.findViewById<EditText>(R.id.edit_login_name)
        val passwd = view?.findViewById<EditText>(R.id.edit_login_passw)

        btn_login.setOnClickListener { view ->
            Log.i("TAG_LOGIN", "Login pressed, name:"+login.text.toString()+"   password:"+passwd.text.toString())
        }
    }

}
