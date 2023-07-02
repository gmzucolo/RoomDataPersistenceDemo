package com.anushka.roomdemo.db

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//TODO: Step 5: Create a ViewModel class for database operations (CRUD)
class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "save"
        clearAllOrDeleteButtonText.value = "clear all"
    }

    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(
            Subscriber(
                id = 0,
                name = name,
                email = email
            )
        )
        inputName.value = ""
        inputEmail.value = ""
    }

    fun clearAllOrDelete() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun insert(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(subscriber)
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(subscriber)
        }
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(subscriber)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}
