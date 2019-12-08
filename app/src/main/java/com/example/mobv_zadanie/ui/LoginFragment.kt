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
            loginORregister("register", edit_login_name.text.toString(), edit_login_passw.text.toString(), false)
        }
        binding.btnLogin.setOnClickListener{
            hideKeyboard()
            loginORregister("login", edit_login_name.text.toString(), edit_login_passw.text.toString(), true)
        }

        //perform auto login if set
        val autoLoginValue = SharedPrefWorker.getBoolean(loginContext, "autoLoginChecked")
        val autologName = SharedPrefWorker.getString(loginContext,"name", "Do Not Login automatically")

        if (autologName != "Do Not Login automatically" && autoLoginValue!!){
            val userPassw = SharedPrefWorker.getString(loginContext, "password", "")
            loginORregister("login", autologName!!, userPassw!!, true)
        }
    }

    private fun loginORregister(action: String, name: String, password: String, startNewFragment: Boolean){
        GlobalScope.launch {
            val response: Deferred<Int> = async (IO) {loginViewModel.pickAPI(action, name, password, loginContext)}
            val code = response.await()
            Log.i("TAG_API", action+" FRAGMENT response: "+ code)
            if (code == 200) {
                activity?.runOnUiThread {
                    playSuccessAnimation(startNewFragment)
                }
            } else {
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

                if (startNewFragment) {
                    //delete fragment from stack - back press wont load this fragment
                    findNavController().popBackStack(R.id.loginFragment, true)
                    //open new fragment
                    findNavController().navigate(R.id.wifiRoomsFragment)
                } else {
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
    }

    private fun hideKeyboard(){
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
}
