package com.example.kriyakosah.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kriyakosah.model.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Query("select * from task_table order by task_priority desc,task_title asc")
    fun getAllTask():Flow<List<Task>>

    @Delete
     suspend fun delete(task: Task)

    @Update
    suspend fun update(newTask:Task)




}