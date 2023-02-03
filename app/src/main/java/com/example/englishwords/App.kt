package com.example.englishwords

import android.app.*
import android.content.Context
import com.example.englishwords.di.*
import com.example.englishwords.notifications.SendNotificationReceiverImpl
import com.example.englishwords.notifications.rememberWordNotification.RememberWordNotificationReceiver
import com.example.englishwords.notifications.reminderNotification.ReminderNotificationReceiver
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject

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
        val sendReceiver by KoinJavaComponent.inject<SendNotificationReceiverImpl>(
            SendNotificationReceiverImpl::class.java
        )
        sendMessages(sendReceiver)
    }

    private fun sendMessages(sendReceiver:SendNotificationReceiverImpl){
        sendReceiver.send(43200000, System.currentTimeMillis()+43200000, ReminderNotificationReceiver())
        sendReceiver.send(7200000, System.currentTimeMillis()+7200000, RememberWordNotificationReceiver())
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