package com.example.mobv_zadanie.ui

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.airbnb.lottie.LottieAnimationView

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.data.webapi.CallAPI
import com.example.mobv_zadanie.data.webapi.UserRefresh
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

    //helper global variable
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var loginContext: Context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setting global variable to use in classes that are in this fragment UI, etc. show Toast from other class
        loginContext = view.context

        btn_register.setOnClickListener { view ->
            hideKeyboard()
            registerUser(edit_login_name.text.toString(), edit_login_passw.text.toString())
        }
        btn_login.setOnClickListener { view ->
            hideKeyboard()
            loginUser(edit_login_name.text.toString(), edit_login_passw.text.toString())
        }
    }

    private fun registerUser(name: String, password: String){

        //CallAPI consumes object UserRequest, produces UserResponse
        CallAPI.setAuthentication(false)
        CallAPI.type.userRegister(UserRequest(name, password, CallAPI.api_key)).enqueue(object: Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(activity, "Oops, something went wrong: "+t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.i("TAG_API", "API response code: "+response.code())

                val animationView1 = view?.findViewById(R.id.lottie_anim_login) as LottieAnimationView
                animationView1.visibility = View.VISIBLE

                if (response.code() == 200){

                    //we want to see only animation, hide other UI components
                    hideUIComponents(View.GONE)

                    //saving server response values into SharedPref
                    SharedPrefWorker.saveString(loginContext, "uid", response.body()!!.uid)
                    SharedPrefWorker.saveString(loginContext, "access", response.body()!!.access)
                    SharedPrefWorker.saveString(loginContext, "refresh", response.body()!!.refresh)
                    //save current user name, password
                    SharedPrefWorker.saveString(loginContext, "name", name)
                    SharedPrefWorker.saveString(loginContext, "password", password)

                    //find animation and run
                    animationView1.setAnimation("success.json")
                    animationView1.addAnimatorListener(object: Animator.AnimatorListener {
                        override fun onAnimationEnd(animation: Animator?) {

                            //delete fragment from stack - back press wont load this fragment
                            view?.findNavController()?.popBackStack(R.id.loginFragment, true)

                            //open new fragment
                            view?.findNavController()?.navigate(R.id.wifiRoomsFragment)
                        }
                        override fun onAnimationRepeat(animation: Animator?) {}
                        override fun onAnimationCancel(animation: Animator?) {}
                        override fun onAnimationStart(animation: Animator?) {}
                    })
                    animationView1.playAnimation()
                }
                else {

                    animationView1.setAnimation("error.json")
                    animationView1.addAnimatorListener(object: Animator.AnimatorListener {
                        override fun onAnimationEnd(animation: Animator?) {

                            //hide animation layout
                            animationView1.visibility = View.GONE
                            hideUIComponents(View.VISIBLE)

                            Toast.makeText(activity, "Oops, server code "+response.code(), Toast.LENGTH_LONG).show()
                        }
                        override fun onAnimationRepeat(animation: Animator?) {}
                        override fun onAnimationCancel(animation: Animator?) {}
                        override fun onAnimationStart(animation: Animator?) {
                            //we want to see only animation, hide other UI components
                            hideUIComponents(View.GONE)
                        }
                    })
                    animationView1.playAnimation()
                }
            }
        })
    }

    private fun loginUser(name: String, password: String){

        //consumes object UserRequest, produces UserResponse
        CallAPI.setAuthentication(false)
        CallAPI.type.userLogin(UserRequest(name, password, CallAPI.api_key)).enqueue(object: Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(activity, "Oops, something went wrong: "+t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.i("TAG_API", "API response code: "+response.code())
                if (response.code() == 200){
                    Toast.makeText(activity, "Success! "+response.code(), Toast.LENGTH_LONG).show()

                    //saving server response values into SharedPref
                    SharedPrefWorker.saveString(loginContext, "uid", response.body()!!.uid)
                    SharedPrefWorker.saveString(loginContext, "access", response.body()!!.access)
                    SharedPrefWorker.saveString(loginContext, "refresh", response.body()!!.refresh)
                    //save current user name, password
                    SharedPrefWorker.saveString(loginContext, "name", name)
                    SharedPrefWorker.saveString(loginContext, "password", password)
                }
                else {
                    Toast.makeText(activity, "Oops, server code "+response.code(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun hideUIComponents(passedCommand: Int){
        edit_login_name.visibility = passedCommand
        edit_login_passw.visibility = passedCommand
        btn_login.visibility = passedCommand
        btn_register.visibility = passedCommand
    }

    private fun hideKeyboard(){
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}
