package com.example.kriyakosah.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.kriyakosah.R


class AlarmReceiver : BroadcastReceiver() {

    private lateinit var Title:String
    private lateinit var Desc:String
    private var id:Int = 0
    private var status :Int =0

    override fun onReceive(context: Context, intent: Intent?) {
        Title = intent?.getStringExtra("title") ?: "Default Title"
        Desc = intent?.getStringExtra("desc") ?: "Default Description"
        if (intent != null) {
            id = intent.getIntExtra("id",0)
        }
        if (intent != null) {
            status = intent.getIntExtra("status",0)
        }
        showNotification(context)

        Log.e("xyz" ,"  $Title  notification")

    }



    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelName = "Task Notifications "
        val channelID = "message_id"

        val channel = NotificationChannel(
            channelID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val notificationBuilder = NotificationCompat.Builder(context,channelID)
            .setContentTitle(Title)
            .setContentText(Desc)
            .setSmallIcon(R.drawable.task)
            .setProgress(100,status,false)

        notificationManager.notify(id,notificationBuilder.build())

    }

}