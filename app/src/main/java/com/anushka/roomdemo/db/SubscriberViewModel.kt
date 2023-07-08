package com.anushka.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anushka.roomdemo.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//TODO: Step 5: Create a ViewModel class for database operations (CRUD)
class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete: Subscriber

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    //TODO: Step 9: Create an private Event variable to handle the response from the database and expose it as LiveData
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "save"
        clearAllOrDeleteButtonText.value = "clear all"
    }

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)
        } else {
            val name = inputName.value ?: ""
            val email = inputEmail.value ?: ""
            insert(Subscriber(id = 0, name = name, email = email))
            inputName.value = ""
            inputEmail.value = ""
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete)
        } else {
            deleteAll()
        }
    }

    fun insert(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            val newRowId = repository.insert(subscriber)
            //TODO: Step 10: Set the status message after the database operation completes and notify the activity
            withContext(Dispatchers.Main) {
                if (newRowId > -1) {
                    statusMessage.value = Event(" Subscriber Inserted Successfully $newRowId")
                } else {
                    statusMessage.value = Event("Error Occurred!")
                }
            }
        }
    }

    fun update(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            val numberOfRows = repository.update(subscriber)
            withContext(Dispatchers.Main) {
                if (numberOfRows > 0) {
                    inputName.value = ""
                    inputEmail.value = ""
                    isUpdateOrDelete = false
                    subscriberToUpdateOrDelete = subscriber
                    saveOrUpdateButtonText.value = "save"
                    clearAllOrDeleteButtonText.value = "clear all"
                    statusMessage.value = Event("$numberOfRows Updated Successfully")
                } else {
                    statusMessage.value = Event("Error Occurred!")

                }
            }
        }
    }

    fun delete(subscriber: Subscriber) {
        viewModelScope.launch(Dispatchers.IO) {
            val numberOfRowsDeleted = repository.delete(subscriber)
            withContext(Dispatchers.Main) {
                if (numberOfRowsDeleted > 0) {
                    inputName.value = ""
                    inputEmail.value = ""
                    isUpdateOrDelete = false
                    subscriberToUpdateOrDelete = subscriber
                    saveOrUpdateButtonText.value = "save"
                    clearAllOrDeleteButtonText.value = "clear all"
                    statusMessage.value = Event("$numberOfRowsDeleted Row(s) Deleted Successfully")
                } else {
                    statusMessage.value = Event("Error Occurred!")

                }
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val numberOfRowsDeleted = repository.deleteAll()
            withContext(Dispatchers.Main) {
                if (numberOfRowsDeleted > 0) {
                    statusMessage.value = Event("$numberOfRowsDeleted Row(s) Deleted Successfully")
                } else {
                    statusMessage.value = Event("Error Occurred")

                }
            }
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "update"
        clearAllOrDeleteButtonText.value = "delete"
    }
}
