package com.example.mobv_zadanie.ui.viewModels

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobv_zadanie.data.DataRepository
import com.example.mobv_zadanie.data.db.model.PostItem
import com.example.mobv_zadanie.data.util.SharedPrefWorker
import com.example.mobv_zadanie.databinding.FragmentPostsBinding
import kotlinx.coroutines.launch

class PostsViewModel(private val repository: DataRepository) : ViewModel() {
    private val _navigateToPost = MutableLiveData<String>()



    val navigateToPosts
        get() = _navigateToPost

    val roomPosts : LiveData<List<PostItem>>
        get() = repository.getPostsSorted()

    fun delete(item: PostItem){
        repository.deletepost(item)
    }

    fun listPosts(context: Context, binding: FragmentPostsBinding) {
        val room = SharedPrefWorker.getString(context, "room", "ssid").toString()
        viewModelScope.launch { repository.postList(context,room)}
    }



}