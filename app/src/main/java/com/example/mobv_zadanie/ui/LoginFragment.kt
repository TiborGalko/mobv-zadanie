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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.mobv_zadanie.R
import com.example.mobv_zadanie.data.util.Injection
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.databinding.FragmentLoginBinding
import com.example.mobv_zadanie.ui.viewModels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

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
        //return inflater.inflate(R.layout.fragment_login, container, false)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )
        binding.lifecycleOwner = this
        loginViewModel = ViewModelProvider(this, Injection.provideViewModelFactory(context!!))
            .get(LoginViewModel::class.java)
        binding.model = loginViewModel

        return binding.root
    }

    //after fragment onCreateView method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setting global variable to use in classes that are in this fragment UI, etc. show Toast from other class (not other fragment/activity!!!)
        loginContext = view.context

        binding.btnRegister.setOnClickListener{
            hideKeyboard()
            GlobalScope.launch {
                val response: Deferred<Int> = async (IO) {loginViewModel.register(edit_login_name.text.toString(), edit_login_passw.text.toString(), loginContext)}
                val code = response.await()
                Log.i("TAG_API", "FRAGMENT response code: "+code)

                if (code == 200){
                    activity?.runOnUiThread {
                        playSuccessAnimation(false)
                    }
                }
                else{
                    activity?.runOnUiThread {
                        playErrorAnimation(code)
                    }
                }
            }
        }
        binding.btnLogin.setOnClickListener{
            hideKeyboard()
            login(edit_login_name.text.toString(), edit_login_passw.text.toString())
        }

        //perform auto login if set
        val autoLoginDefault = SharedPrefWorker.getString(loginContext,"autoLogName", "Do Not Login automatically")
        val autoLoginValue = SharedPrefWorker.getBoolean(loginContext, "autoLoginChecked")
        if (autoLoginDefault != "Do Not Login automatically" && autoLoginValue!!){
            val userName = SharedPrefWorker.getString(loginContext, "autoLogName", "")
            val userPassw = SharedPrefWorker.getString(loginContext, "autoLogPassw", "")
            Log.i("TAG_API", "auto login: "+ userName+ " "+ userPassw)
            login(userName!!, userPassw!!)
        }
    }

    private fun login(name: String, password: String){
        GlobalScope.launch {
            val response: Deferred<Int> = async (IO) {loginViewModel.login(name, password, loginContext)}
            val code = response.await()
            Log.i("TAG_API", "login FRAGMENT response: "+ code)
            if (code == 200){
                activity?.runOnUiThread {
                    playSuccessAnimation(true)
                }
            }
            else{
                activity?.runOnUiThread {
                    playErrorAnimation(code)
                }
            }
        }
    }

    private fun playSuccessAnimation(startNewFragment: Boolean){
        lottie_anim_login.visibility = View.VISIBLE
        lottie_anim_login.setAnimation("success.json")
        lottie_anim_login.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {

                //save name and password if auto login checked
                saveUserLoginOption()
                if (startNewFragment){
                    //delete fragment from stack - back press wont load this fragment
                    findNavController().popBackStack(R.id.loginFragment, true)
                    //open new fragment
                    findNavController().navigate(R.id.wifiRoomsFragment)
                }
                else {
                    lottie_anim_login.visibility = View.GONE
                    hideUIComponents(View.VISIBLE)
                }
            }
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {
                hideUIComponents(View.GONE)
            }
        })
        lottie_anim_login.playAnimation()
    }

    private fun playErrorAnimation(response: Int){
        lottie_anim_login.visibility = View.VISIBLE
        lottie_anim_login.setAnimation("error.json")
        lottie_anim_login.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {

                //hide animation layout
                lottie_anim_login.visibility = View.GONE
                hideUIComponents(View.VISIBLE)
                //Toast.makeText(activity, "Oops, server code "+response, Toast.LENGTH_LONG).show()
            }
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {
                hideUIComponents(View.GONE)
            }
        })
        lottie_anim_login.playAnimation()
    }

    private fun hideUIComponents(passedCommand: Int){
        edit_login_name.visibility = passedCommand
        edit_login_passw.visibility = passedCommand
        btn_login.visibility = passedCommand
        btn_register.visibility = passedCommand
        switch_stay.visibility = passedCommand
    }

    private fun hideKeyboard(){
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    //maybe save in DataRep / LocalCache
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
