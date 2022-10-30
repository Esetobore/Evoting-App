package com.example.evoting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.evoting.UserRepository

abstract class UserViewModel() : ViewModel() {

    private val repository : UserRepository
    private val MvoterList =  MutableLiveData<List<User>>()
    val allusers : LiveData<List<User>> = MvoterList

    init {
        repository = UserRepository().getInstance()
        repository.loadUsers(MvoterList)
    }
}