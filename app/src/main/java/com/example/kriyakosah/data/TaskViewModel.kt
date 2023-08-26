package com.example.kriyakosah.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kriyakosah.data.repository.repository
import com.example.kriyakosah.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val repository:repository ) :ViewModel(){

    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    var taskList = _taskList.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO){
          repository.getTAllTask().distinctUntilChanged().collect{
              if(it.isEmpty()){
                _taskList.value = emptyList()
              }
              else{
                 _taskList.value = it
              }
          }
        }

    }

    fun addTask(task: Task) = viewModelScope.launch{repository.insertTask(task)}

    fun deleteTask(task:Task) = viewModelScope.launch { repository.delete(task) }

    fun updateTask(newTask:Task) = viewModelScope.launch { repository.update(newTask) }
}