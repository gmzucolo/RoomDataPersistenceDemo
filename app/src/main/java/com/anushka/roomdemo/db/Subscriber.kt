package com.anushka.roomdemo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO: Step 1: Create a data class for the table (Entity)
@Entity(tableName = "subscriber_data_table")
data class Subscriber (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriber_name")
    val id : Int,

    @ColumnInfo(name = "subscriber_id")
    val name : String,

    @ColumnInfo(name = "subscriber_email")
    val email : String

)