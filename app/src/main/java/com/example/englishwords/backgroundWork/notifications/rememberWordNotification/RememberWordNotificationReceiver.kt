package com.example.englishwords.backgroundWork.notifications.rememberWordNotification

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
import com.example.englishwords.viewModels.MainViewModel
import com.example.englishwords.viewModels.MessageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import kotlin.random.Random

class RememberWordNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationViewModel by inject<NotificationViewModel>(
            NotificationViewModel::class.java
        )
        if (!notificationViewModel.isWordNotifications.value) return
        val messageViewModel by inject<MessageViewModel>(MessageViewModel::class.java)
        if (messageViewModel.isAppOn.value) return
        val mainViewModel by inject<MainViewModel>(MainViewModel::class.java)
        mainViewModel.getAllFromRoom()
        val scope = CoroutineScope(SupervisorJob())
        scope.launch {
            delay(4000)
            val allModeldb = mainViewModel.getAllRoomData.value ?: return@launch
            val modeldb = allModeldb[Random.nextInt(from = 0, until = allModeldb.size)]
            var allDefinitions = ""
            var allExamples = ""
            try {
                for (i in 0..if (modeldb.definitions.size < 2) modeldb.definitions.size - 1 else 1) {
                    allDefinitions += "    ${modeldb.definitions[i]}\n"
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            try {
                val localBound = if (modeldb.examples.size < 2) modeldb.examples.size - 1 else 1
                for (i in 0..localBound) {
                    allExamples += "    ${modeldb.examples[i]}"
                    if (i != localBound) allExamples += "\n"
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
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
                        return@launch
                    }
                    notify(
                        Random.nextInt(),
                        NotificationCompat.Builder(context.applicationContext, "2")
                            .setSmallIcon(androidx.core.R.drawable.notification_bg)
                            .setContentTitle("Do you remember the meaning of ${modeldb.word}?")
                            .setContentText(
                                "${if (modeldb.note.isNotEmpty()) "Note: ${modeldb.note}\n" else ""}Definitions:\n${allDefinitions}"
                            )
                            .setStyle(
                                NotificationCompat.BigTextStyle()
                                    .bigText("${if (modeldb.note.isNotEmpty()) "Note: ${modeldb.note}\n" else ""}Definitions:\n${allDefinitions}Examples:\n$allExamples")
                            )
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .build()
                    )
                }
            }
        }
    }
}