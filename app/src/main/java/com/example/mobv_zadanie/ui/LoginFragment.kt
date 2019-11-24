package com.example.mobv_zadanie.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.webapi.CallAPI
import com.example.mobv_zadanie.data.webapi.UserRequest
import com.example.mobv_zadanie.data.webapi.UserResponse
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        Log.i("TAG_API", "Landing page" )
        val login = view.findViewById<EditText>(R.id.edit_login_name)
        val passwd = view.findViewById<EditText>(R.id.edit_login_passw)

        btn_login.setOnClickListener { view ->
            Log.i("TAG_API", "Login pressed, name:"+login.text.toString()+"   password:"+passwd.text.toString())

            //todo change values

            CallAPI.type.userRegister(UserRequest("a", "a", "c95332ee022df8c953ce470261efc695ecf3e784")).enqueue(object: Callback<UserResponse>{
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.i("TAG_API", "API call FAILED: "+t.message)
                }

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    Log.i("TAG_API", "API call SUCCESS")


                }

            })
            //Call <UserRequest> call
        }
    }

}
