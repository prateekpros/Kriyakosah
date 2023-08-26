package com.example.kriyakosah.Card


import android.R
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NotificationImportant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.kriyakosah.MainActivity
import com.example.kriyakosah.model.Task
import com.example.kriyakosah.ui.theme.Purple40


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedCard(tasks:Task,Remove:(task:Task)->Unit, save:(newTask:Task)->Unit,activity: MainActivity
){

    val task by rememberUpdatedState(tasks)

    var alarm by remember {
        mutableStateOf(false)
    }

    var colour by remember {
        mutableStateOf(Color.White)
    }

    var notification by remember {
        mutableStateOf(task.notification)
    }

    var expandedState by remember { mutableStateOf(false) }

    var editedDescription by remember{ mutableStateOf(task.desc) }

    var priority by remember {
        mutableStateOf(task.priority)
    }
    var priorityColor by remember {
        mutableStateOf(Color.Gray)
    }

    var status by remember {
        mutableFloatStateOf(task.status)
    }
    var time by remember {
        mutableStateOf(task.notificationTime )
    }

    var timer by remember {
        mutableStateOf(false)
    }

    var notificationColor by remember {
        mutableStateOf(Color.Gray)
    }

    alarm = faltuFunction(time) <= faltuFunction(getCurrentTimeIn24HourFormat())

    notificationColor = if(notification) Purple40 else Color.Gray

    Log.e("xyz","id of ${task.title} = ${task.id}")

    priorityColor =if(priority) Purple40 else Color.Gray
    val context = LocalContext.current


    Card(modifier = Modifier
        .padding(top = 5.dp, start = 0.dp, end = 0.dp, bottom = 5.dp)
        .fillMaxWidth(1f)
        .animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        ),
        shape = RoundedCornerShape(4.dp),
        onClick = {expandedState = !expandedState
        save(task)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth(1f)
            .padding(15.dp)) {

            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){


                Text(
                    task.title, modifier = Modifier.weight(5f), fontWeight = FontWeight.Bold
                )

                if(!expandedState && notification)
                        Icon(imageVector = Icons.Default.AlarmOn, contentDescription ="" ,Modifier.weight(1f))

                Spacer(modifier = Modifier.weight(1f))
                if(!expandedState){
                    IconButton(onClick = { expandedState = !expandedState }, modifier = Modifier
                        .padding(5.dp)
                        .weight(1f),) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null,Modifier.weight(1f) )
                    }

                }

                if(expandedState) {
                    IconButton(onClick = { Remove(task)
                    }, Modifier.weight(2f)) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null,)
                    }

                    IconButton(onClick = { priority = !priority },Modifier.weight(2f)) {
                        Icon(imageVector = Icons.Default.Star, contentDescription =" ", tint = priorityColor )
                    }

                    TextButton(onClick = { expandedState=!expandedState

                        task.desc = editedDescription
                        task.status = status
                        task.priority = priority
                        task.notificationTime = time
                        task.notification = notification

                        save(task)


                    }, modifier = Modifier.weight(3f) ) {
                        Text("Save")
                    }
                }
            }

            if(expandedState){

                Row(Modifier
                    .padding(top=10.dp, bottom = 0.dp, start = 0.dp, end = 0.dp))
                {
                    OutlinedTextField(
                        value = editedDescription, onValueChange = { editedDescription = it
                                                                   },
                        keyboardOptions = KeyboardOptions.Default.copy( autoCorrect = true),

                             modifier = Modifier.weight(8f),
                        placeholder = { Text(text = "Description of Task") },

                    )

                }



                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {

                        IconButton(onClick = { notification = !notification },Modifier.weight(1f)
                        ) {
                            Icon(imageVector = Icons.Default.NotificationImportant, contentDescription ="" , tint = notificationColor)
                        }

                        Spacer(modifier = Modifier.weight(2f))

                        TextButton(onClick = { timer = !timer }, modifier = Modifier
                            .weight(1f)) {
                            Text(text = time,color = Purple40, fontWeight = FontWeight.Bold)
                        }

                    }

                    colour = DynamicColorSlider(value = status, onValueChange = {status= it}, valueRange = 0f..100f)



                if(timer)
                    TimePicker(onTimeSelected = { timer = false;time = it })

                }

               if(notification && (faltuFunction(time) > faltuFunction(getCurrentTimeIn24HourFormat()))){
                   Log.e("xyz"," called set alarm fun  ")
                   activity.setAlarm(task,context)

               }
               if(notification && alarm){
                    notification = false
                    task.notification = false
                    save(task)
                }

            }

        }


    }





@Composable
fun DynamicColorSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
):Color {
    // Calculate the color based on slider value
    val startColor = Color.Red
    val endColor = Color.Green
    val fraction = value / valueRange.endInclusive
    val interpolatedColor = Color(
        red = startColor.red + fraction * (endColor.red - startColor.red),
        green = startColor.green + fraction * (endColor.green - startColor.green),
        blue = startColor.blue + fraction * (endColor.blue - startColor.blue)
    )

    Slider(
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        colors = SliderDefaults.colors(
            thumbColor = interpolatedColor,
            activeTrackColor = interpolatedColor,
            inactiveTrackColor = interpolatedColor.copy(alpha = 0.4f),
        )
    )
    return interpolatedColor
}








@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCardDialog(value: String, setShowDialog: (Boolean) -> Unit, setValue: (String, String, Boolean) -> Unit) {

    val txtFieldError = remember { mutableStateOf("") }
    var taskField by remember { mutableStateOf(value) }
    var descField by remember { mutableStateOf("")    }
    var priority by remember {
        mutableStateOf(false)
    }
    var priorityColor by remember {
        mutableStateOf(Color.Gray)
    }

    priorityColor = if(priority) Color.Yellow else Color.Gray

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "New Task",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        IconButton(onClick = { priority = !priority }) {

                            Icon(imageVector = Icons.Outlined.Star, contentDescription = "",
                                tint = priorityColor
                            )
                            
                        }
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "",
                            tint = colorResource(R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { setShowDialog(false) }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),

                        placeholder = { Text(text = "Task Name") },
                        value = taskField,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
                        onValueChange = {
                            taskField = it
                        },
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                        ,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),

                        placeholder = { Text(text = "Description") },
                        value = descField,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
                        onValueChange = {
                            descField = it
                        },
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                if (taskField.isEmpty()) {
                                    txtFieldError.value = "Field can not be empty"
                                    return@Button
                                }
                                setValue(taskField,descField,priority)
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Text(text = "Done")
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}




