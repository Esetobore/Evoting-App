package com.example.evoting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.evoting.UserRepository

abstract class CanViewModel() : ViewModel() {

    private val repository : CanRepository
    private val CanList =  MutableLiveData<List<User>>()
    val allusers : LiveData<List<User>> = CanList

    init {
        repository = CanRepository().getInstance()
        repository.loadUsers(CanList)
    }
}