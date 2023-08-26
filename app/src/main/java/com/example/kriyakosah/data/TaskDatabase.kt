package com.example.kriyakosah.data


import androidx.room.Database

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kriyakosah.data.converters.DateConverter
import com.example.kriyakosah.model.Task

@TypeConverters(value = [DateConverter::class])
@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase :RoomDatabase(){

    abstract fun taskDao():TaskDao


}