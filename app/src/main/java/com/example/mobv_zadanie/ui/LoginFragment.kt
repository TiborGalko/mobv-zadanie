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
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.findNavController
import com.airbnb.lottie.LottieAnimationView

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

    //after fragment onCreateView method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setting global variable to use in classes that are in this fragment UI, etc. show Toast from other class (not other fragment/activity!!!)
        loginContext = view.context

        btn_register.setOnClickListener { view ->
            registerUser(edit_login_name.text.toString(), edit_login_passw.text.toString())
        }
        btn_login.setOnClickListener { view ->
            loginUser(edit_login_name.text.toString(), edit_login_passw.text.toString())
        }

        //perform auto login if set
        val autoLoginDefault = SharedPrefWorker.getString(loginContext,"autoLogName", "Do Not Login automatically")
        val autoLoginValue = SharedPrefWorker.getBoolean(loginContext, "autoLoginChecked")
        if (autoLoginDefault != "Do Not Login automatically" && autoLoginValue!!){
            val userName = SharedPrefWorker.getString(loginContext, "autoLogName", "")
            val userPassw = SharedPrefWorker.getString(loginContext, "autoLogPassw", "")
            Log.i("TAG_API", "auto login: "+ userName+ " "+ userPassw)
            loginUser(userName!!, userPassw!!)
        }
    }

    private fun registerUser(name: String, password: String){

        //CallAPI consumes object UserRequest, produces UserResponse
        CallAPI.setAuthentication(false)
        CallAPI.type.userRegister(UserRequest(name, password, CallAPI.api_key)).enqueue(object: Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                //Toast.makeText(activity, "Oops, something went wrong: "+t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.i("TAG_API", "API response code: "+response.code())

                hideKeyboard()
                val animation = view?.findViewById(R.id.lottie_anim_login) as LottieAnimationView
                animation.visibility = View.VISIBLE

                if (response.code() == 200){
                    //saving server response values into SharedPref
                    SharedPrefWorker.saveString(loginContext, "uid", response.body()!!.uid)
                    SharedPrefWorker.saveString(loginContext, "access", response.body()!!.access)
                    SharedPrefWorker.saveString(loginContext, "refresh", response.body()!!.refresh)
                    //save current user name, password
                    SharedPrefWorker.saveString(loginContext, "name", name)
                    SharedPrefWorker.saveString(loginContext, "password", password)

                    playSuccessAnimation(animation, false)
                }
                else {
                    playErrorAnimation(animation, response)
                }
            }
        })
    }

    private fun loginUser(name: String, password: String){

        //consumes object UserRequest, produces UserResponse
        CallAPI.setAuthentication(false)
        CallAPI.type.userLogin(UserRequest(name, password, CallAPI.api_key)).enqueue(object: Callback<UserResponse>{
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                //Toast.makeText(activity, "Oops, something went wrong: "+t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                hideKeyboard()

                val animation = view?.findViewById(R.id.lottie_anim_login) as LottieAnimationView
                animation.visibility = View.VISIBLE

                if (response.code() == 200){
                    //saving server response values into SharedPref
                    SharedPrefWorker.saveString(loginContext, "uid", response.body()!!.uid)
                    SharedPrefWorker.saveString(loginContext, "access", response.body()!!.access)
                    SharedPrefWorker.saveString(loginContext, "refresh", response.body()!!.refresh)
                    //save current user name, password
                    SharedPrefWorker.saveString(loginContext, "name", name)
                    SharedPrefWorker.saveString(loginContext, "password", password)

                    playSuccessAnimation(animation, true)
                }
                else {
                    playErrorAnimation(animation, response)
                }
            }
        })
    }

    private fun playSuccessAnimation(view: LottieAnimationView, startNewFragment: Boolean){
        //find animation and run
        view.setAnimation("success.json")
        view.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {

                //save name and password if auto login checked
                saveUserLoginOption()
                if (startNewFragment){
                    //delete fragment from stack - back press wont load this fragment
                    view.findNavController().popBackStack(R.id.loginFragment, true)
                    //open new fragment
                    view.findNavController().navigate(R.id.wifiRoomsFragment)
                }
                else {
                    view.visibility = View.GONE
                    hideUIComponents(View.VISIBLE)
                }
            }
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {
                hideUIComponents(View.GONE)
            }
        })
        view.playAnimation()
    }

    private fun playErrorAnimation(view: LottieAnimationView, response: Response<UserResponse>){
        view.setAnimation("error.json")
        view.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {

                //hide animation layout
                view.visibility = View.GONE
                hideUIComponents(View.VISIBLE)
                Toast.makeText(activity, "Oops, server code "+response.code(), Toast.LENGTH_LONG).show()
            }
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {
                hideUIComponents(View.GONE)
            }
        })
        view.playAnimation()
    }

    private fun hideUIComponents(passedCommand: Int){
        edit_login_name.visibility = passedCommand
        edit_login_passw.visibility = passedCommand
        btn_login.visibility = passedCommand
        btn_register.visibility = passedCommand
        switch_stay.visibility = passedCommand
    }

    private fun hideKeyboard(){
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    private fun saveUserLoginOption(){
        if (switch_stay.isChecked) {
            SharedPrefWorker.saveBoolean(loginContext, "autoLoginChecked", true)
            SharedPrefWorker.saveString(loginContext, "autoLogName", edit_login_name.text.toString())
            SharedPrefWorker.saveString(loginContext, "autoLogPassw", edit_login_passw.text.toString())
        } else {
            SharedPrefWorker.saveString(loginContext, "autoLogName", "Do Not Login automatically")
        }
    }
}
