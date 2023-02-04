package com.example.englishwords

import android.app.*
import android.content.Context
import com.example.englishwords.backgroundWork.deleteOldWord.DeleteOldWordReceiver
import com.example.englishwords.backgroundWork.DoReceiverImpl
import com.example.englishwords.di.*
import com.example.englishwords.backgroundWork.notifications.SendNotificationReceiverImpl
import com.example.englishwords.backgroundWork.notifications.rememberWordNotification.RememberWordNotificationReceiver
import com.example.englishwords.backgroundWork.notifications.reminderNotification.ReminderNotificationReceiver
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(mainDi, viewModelsModule, networkModule, databaseModule, otherModule))
        }
        createNotificationChannel("1", "Reminder", "It's time to go ahead")
        createNotificationChannel("2", "Word reminder", "Do you remember the meaning of word?")
        val sendReceiver by inject<SendNotificationReceiverImpl>(
            SendNotificationReceiverImpl::class.java
        )
        val doReceiver by inject<DoReceiverImpl>(DoReceiverImpl::class.java)
        sendMessages(sendReceiver)
        doReceiver(doReceiver)
    }

    private fun doReceiver(doReceiverImpl: DoReceiverImpl){
        doReceiverImpl.send(86400000, 86400000,DeleteOldWordReceiver())
    }

    private fun sendMessages(sendReceiver: SendNotificationReceiverImpl){
        val calendar = Calendar.getInstance()
        sendReceiver.send(86400000, calendar.timeInMillis+86400000, ReminderNotificationReceiver())
        sendReceiver.send(10800000, calendar.timeInMillis+10800000, RememberWordNotificationReceiver())
    }

    private fun createNotificationChannel(
        channelId: String,
        name: String,
        descriptionText: String
    ) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}