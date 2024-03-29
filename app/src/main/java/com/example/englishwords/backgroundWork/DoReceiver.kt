package com.example.englishwords.backgroundWork

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

interface DoReceiverInterface {
    val context: Context
}

class DoReceiverImpl(override val context: Context):
    DoReceiverInterface {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    private val calendar = Calendar.getInstance()
    fun send(intervalAtMillis: Long,triggerAtMillis: Long,receiver: BroadcastReceiver){
        val intent = Intent(context, receiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, alarmManager.hashCode(), intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,calendar.timeInMillis + triggerAtMillis, intervalAtMillis, pendingIntent)
    }

}