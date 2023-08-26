package com.example.kriyakosah.Card




import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun TimePicker(onTimeSelected: (String) -> Unit) {
    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()

    var selectedTime by remember { mutableStateOf("") }

    val mTimePickerDialog = remember {
        TimePickerDialog(
            mContext,
            { _, hour, minute ->
                val newTime = "$hour:$minute"
                selectedTime = newTime
                onTimeSelected(newTime)
            },
            mCalendar[Calendar.HOUR_OF_DAY],
            mCalendar[Calendar.MINUTE],
            false
        )
    }

    DisposableEffect(mTimePickerDialog) {
        mTimePickerDialog.show()

        onDispose {
            mTimePickerDialog.dismiss()
        }
    }
}



fun getCurrentTimeIn24HourFormat(): String {
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return currentTime.format(formatter)
}

fun faltuFunction(time:String):Int{
    val parts = time.split(":")
    return parts[0].toInt()*100 + parts[1].toInt()

}

fun convertToMilliseconds(timeString: String): Long {

    val parts = timeString.split(":")
    Log.e("time","time : ${parts[0].toLong()} : ${parts[1].toLong()}")
    var time  = 0L
    if (parts.size == 2) {
        val hour = parts[0].toLong()
        val minute = parts[1].toLong()


        time = (hour * 3600 + minute * 60) * 1000
        Log.e("time"," time = $minute")

    }
   return time
}






