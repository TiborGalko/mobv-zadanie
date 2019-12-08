package com.example.mobv_zadanie.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mobv_zadanie.data.DataRepository

class LoginViewModel(private val repository: DataRepository) : ViewModel() {

    suspend fun pickAPI(apiName: String, name: String, password: String, context: Context): Int{
        if (apiName == "register"){
            return repository.register(name, password, context)
        }
        else {
            return repository.login(name, password, context)
        }
    }
}