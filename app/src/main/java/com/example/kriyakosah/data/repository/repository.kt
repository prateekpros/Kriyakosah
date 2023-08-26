package com.example.kriyakosah.data.repository

import androidx.room.Insert
import com.example.kriyakosah.data.TaskDao
import com.example.kriyakosah.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject

class repository @Inject constructor(
    private val  taskDao: TaskDao
) {

    fun getTAllTask():Flow<List<Task>> = taskDao.getAllTask().flowOn(Dispatchers.IO).conflate()

    suspend fun insertTask(task: Task){
        taskDao.insert(task)
    }
    suspend fun delete(task: Task){
        taskDao.delete(task)
    }

    suspend fun update(newTask:Task){
        taskDao.update(newTask)
    }

//    suspend fun deleteByUuid(uuid: UUID) {
//        taskDao.deleteByUuid(uuid)
//    }
}