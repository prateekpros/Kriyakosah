package com.example.kriyakosah.data.di

import android.content.Context
import androidx.room.Room
import com.example.kriyakosah.data.TaskDao
import com.example.kriyakosah.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTaskDao(taskDatabase: TaskDatabase):TaskDao = taskDatabase.taskDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context):TaskDatabase
    = Room.databaseBuilder(context,TaskDatabase::class.java,"task_DB")
        .fallbackToDestructiveMigration().build()
}