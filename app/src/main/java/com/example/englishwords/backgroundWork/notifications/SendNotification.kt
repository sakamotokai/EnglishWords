package com.example.englishwords.backgroundWork.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

interface SendNotificationReceiverInterface {
    val context: Context
}

class SendNotificationReceiverImpl(override val context: Context):
    SendNotificationReceiverInterface {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun send(intervalAtMillis: Long,triggerAtMillis: Long,receiver: BroadcastReceiver){
        val intent = Intent(context, receiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, alarmManager.hashCode(), intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,triggerAtMillis, intervalAtMillis, pendingIntent)
    }

}