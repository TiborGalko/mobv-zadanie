package com.example.mobv_zadanie.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.mobv_zadanie.data.DataRepository

class LoginViewModel(private val repository: DataRepository) : ViewModel() {

    suspend fun register(name: String, password: String): Int {
        return repository.register(name, password)
    }
}