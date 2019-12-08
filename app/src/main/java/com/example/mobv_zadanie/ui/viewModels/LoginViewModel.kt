package com.example.mobv_zadanie.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.mobv_zadanie.data.DataRepository

class LoginViewModel(private val repository: DataRepository) : ViewModel() {

    suspend fun register(name: String, password: String, context: Context): Int {
        return repository.register(name, password, context)
    }

    suspend fun login(name: String, password: String, context: Context): Int {
        return repository.login(name, password, context)
    }
}