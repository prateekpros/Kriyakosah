package com.example.kriyakosah


import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.kriyakosah.Card.CustomCardDialog
import com.example.kriyakosah.Card.ExpandedCard
import com.example.kriyakosah.Card.getCurrentTimeIn24HourFormat
import com.example.kriyakosah.data.TaskViewModel
import com.example.kriyakosah.model.Task
import com.example.kriyakosah.notification.AlarmReceiver
import com.example.kriyakosah.ui.theme.KriyakosahTheme
import com.example.kriyakosah.ui.theme.Purple40
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        setContent {

              val taskViewModel:TaskViewModel by viewModels()
            KriyakosahTheme {

                Kriyakosah(taskViewModel = taskViewModel,this)

            }
        }
    }


    fun setAlarm(task:Task,context: Context)
    {
        Log.e("xyz"," in set alarm fun for ${task.title} ")
        val Title:String = task.title
        val Desc:String = task.desc
        val timeString:String = task.notificationTime
        val id:Int = task.id
        val status:Int = task.status.toInt()
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {putExtra("title", Title)
            putExtra("desc", Desc)
        putExtra("id",id)
        putExtra("status",status) }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent, PendingIntent.FLAG_IMMUTABLE
        )
        val time = calculateTriggerTime(timeString)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,time, pendingIntent)

    }


    private fun calculateTriggerTime(time:String): Long {
        val parts = time.split(":")
       val hourOfDay: Int = parts[0].toInt()
        val minute: Int = parts[1].toInt()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // If the specified time is in the past, move to the next day
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return calendar.timeInMillis
    }



}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Kriyakosah(taskViewModel:TaskViewModel,activity: MainActivity){
    var addState by remember { mutableStateOf(false) }

    var homeColor by remember {
        mutableStateOf(Color.Gray)
    }
    var home by remember {
        mutableStateOf(true)
    }
    var graph by remember {
        mutableStateOf(false)
    }
    var graphColor by remember {
        mutableStateOf(Color.Gray)
    }

    homeColor = if(home) Color.White else Color.Gray
    graphColor = if(graph) Color.White else Color.Gray


    if (addState && home) {
        CustomCardDialog(
            value = "",
            setShowDialog = { addState = it },
            setValue = { task, desc, priority ->
                taskViewModel.addTask(
                    Task(
                        title = task,
                        desc = desc,
                        priority = priority,
                        notificationTime = getCurrentTimeIn24HourFormat(),
                        status = 0f
                    )
                )
            }
        )
    }

    Scaffold(
        topBar = {TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Purple40),title = {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = R.drawable.task), contentDescription ="", modifier = Modifier
                .weight(2f)
                .size(40.dp)
                .padding(start = 5.dp, top = 0.dp, bottom = 0.dp, end = 10.dp))
            Text(text ="Kriyakosah", modifier = Modifier
                .padding(vertical = 2.dp, horizontal = 20.dp)
                .weight(6f) )}}
        , modifier = Modifier
            .fillMaxWidth(1f)
    )}

    , bottomBar ={    BottomAppBar(Modifier.padding(start = 0.dp, bottom = 0.dp, end = 0.dp, top = 5.dp), containerColor = Purple40) {

            IconButton(onClick = { home = true;graph = false },Modifier.weight(2f)) {
                Icon(imageVector = Icons.Default.Home, contentDescription ="" , tint =  homeColor)
            }

                AnimatedVisibility(
                    visible = home,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ){
                    FloatingActionButton(
                        onClick = {  addState = !addState  },

                        //elevation = FloatingActionButtonDefaults.elevation(10.dp) ,
                        containerColor = Color(0xFF957ABE),
                        // contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier.size(if (home) 60.dp else 0.dp)
                            .animateContentSize()
                            .size(60.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add",
                            tint = Color.White,
                        )
                    }
                }


            IconButton(onClick = { home = false; graph = true },modifier = Modifier.weight(2f)) {
                Icon(imageVector = Icons.Default.AutoGraph, contentDescription ="" , tint =  graphColor)
            }
        }}
    ) {

        MyApp(taskViewModel,
            onRemoveTask = {taskViewModel.deleteTask(it)},
            update = {newTask->
                taskViewModel.updateTask(newTask)},activity
        )

    }

    }






@SuppressLint("SuspiciousIndentation")
@Composable
fun MyApp(
    taskViewModel: TaskViewModel,
    onRemoveTask: (Task) -> Unit,
    update: (Task) -> Unit,
    activity: MainActivity
) {
    val tasks by taskViewModel.taskList.collectAsState()

        LazyColumn(
            Modifier.padding(top= 65.dp, bottom = 80.dp, start = 0.dp, end = 0.dp)
        ) {
            items(tasks) { task ->
                ExpandedCard(
                    tasks = task,
                    Remove = { t -> onRemoveTask(t) },
                    save = { newTask -> update(newTask) },
                    activity
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

}








