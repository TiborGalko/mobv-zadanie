package com.example.mobv_zadanie.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.data.db.model.UserItem

class TestViewModel(private val repository: DataRepository) : ViewModel() {
    val saved : LiveData<String> = MutableLiveData()

    val loaded : LiveData<List<UserItem>>
        get() = repository.getLoaded()

}