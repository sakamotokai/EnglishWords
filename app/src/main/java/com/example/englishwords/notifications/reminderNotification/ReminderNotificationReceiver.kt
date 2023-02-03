package com.example.englishwords.notifications.reminderNotification

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.englishwords.MainActivity
import com.example.englishwords.screens.settingsScreen.notifications.NotificationViewModel
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import com.example.englishwords.viewModels.MessageViewModel
import org.koin.java.KoinJavaComponent
import java.time.LocalTime
import kotlin.random.Random

class ReminderNotificationReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationViewModel by KoinJavaComponent.inject<NotificationViewModel>(
            NotificationViewModel::class.java
        )
        if (!notificationViewModel.isNotifications.value) return
        val messageViewModel by KoinJavaComponent.inject<MessageViewModel>(MessageViewModel::class.java)
        if (messageViewModel.isAppOn.value) return
        //TODO("Check in future. It should works but i'm insecure")
        if(LocalTime.now().hour - messageViewModel.lastStartHour.value <8){
            if(LocalTime.now().hour - messageViewModel.lastStartHour.value <0){
                if(24-messageViewModel.lastStartHour.value+LocalTime.now().hour < 8) return
            } else return
        }
        if (context != null) {
            val localIntent = Intent(
                context.applicationContext,
                MainActivity::class.java
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(
                    context.applicationContext,
                    0,
                    localIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                notify(
                    Random.nextInt(), NotificationCompat.Builder(context.applicationContext, "1")
                        .setSmallIcon(androidx.core.R.drawable.notification_bg)
                        .setContentTitle("It's time to go ahead")
                        .setContentText("You haven't opened our application for a long time. It's time to learn new words and repeat old ones")
                        .setStyle(
                            NotificationCompat.BigTextStyle()
                                .bigText("You haven't opened our application for a long time. It's time to learn new words and repeat old ones")
                        )
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build()
                )
            }
        }
    }
}