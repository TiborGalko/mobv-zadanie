package com.example.mobv_zadanie.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.SharedPrefWorker
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

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "mobv_zadanie"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login = view.findViewById<EditText>(R.id.edit_login_name)
        val passwd = view.findViewById<EditText>(R.id.edit_login_passw)

        btn_register.setOnClickListener { view ->
            registerUser(login.text.toString(), passwd.text.toString())
        }
        btn_login.setOnClickListener { view ->
            //todo
        }
    }

    //API for registering user
    private fun registerUser(name: String, password: String){
        Log.i("TAG_API", "Login pressed, name:"+name+"   password:"+password)

        //consumes object UserRequest, produces UserResponse
        CallAPI.setAuthentication(false)
        CallAPI.type.userRegister(UserRequest(name, password, CallAPI.api_key)).enqueue(object: Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(activity, "Oops, something went wrong: "+t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.i("TAG_API", "API response code: "+response.code())
                if (response.code() == 200){
                    Toast.makeText(activity, "Success! "+response.code(), Toast.LENGTH_LONG).show()

                    ///saving server response values into SharedPref
                    SharedPrefWorker.saveString(view!!.context, "uid", response.body()!!.uid)
                    SharedPrefWorker.saveString(view!!.context, "access", response.body()!!.access)
                    SharedPrefWorker.saveString(view!!.context, "refresh", response.body()!!.refresh)
                }
                else {
                    Toast.makeText(activity, "Oops, server code "+response.code(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}
