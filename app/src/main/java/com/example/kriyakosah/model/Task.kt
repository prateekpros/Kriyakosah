package com.example.kriyakosah.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID
import kotlin.random.Random

@Entity(tableName = "task_table")
data class Task  constructor(

    @PrimaryKey(autoGenerate = true)
//    val uuid: UUID = UUID.randomUUID(),
    var id:Int = 0,//uuid.leastSignificantBits.toInt() + uuid.mostSignificantBits.toInt(),
    @ColumnInfo(name = "task_title")
    var title:String,
    @ColumnInfo(name = "task_desc")
    var desc:String,
    @ColumnInfo(name = "task_priority")
    var priority:Boolean,
    var status:Float = 0f,
    var notification:Boolean = false,
    var notificationTime:String,
    //var alarm :Boolean = false


)
