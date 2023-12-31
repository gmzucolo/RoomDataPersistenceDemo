package com.anushka.roomdemo.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//TODO: Step 6: Create a ViewModelFactory class for database operations (CRUD)
class SubscriberViewModelFactory(private val repository: SubscriberRepository) :
    ViewModelProvider.Factory {
    //boilerplate for ViewModelFactory
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberViewModel::class.java)) {
            return SubscriberViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}