package com.anushka.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*

//TODO: Step 2: Create a DAO interface for database operations (CRUD)
@Dao
interface SubscriberDAO {

    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber): Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers():LiveData<List<Subscriber>>
}