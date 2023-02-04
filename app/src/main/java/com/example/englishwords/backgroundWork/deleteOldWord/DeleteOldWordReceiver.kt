package com.example.englishwords.backgroundWork.deleteOldWord

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.englishwords.viewModels.MainViewModel
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDate

class DeleteOldWordReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val mainViewModel by inject<MainViewModel>(MainViewModel::class.java)
            val allRoomData = mainViewModel.getAllRoomData.value
            val localDate = LocalDate.now()
            val currentDay = localDate.dayOfMonth
            allRoomData?: return
        try {
            allRoomData.forEach { modeldb ->
                if (currentDay - modeldb.data[0].toInt() >= 7) {
                    mainViewModel.delete(modeldb)
                } else if (currentDay - modeldb.data[0].toInt() < 0) {
                    if (31 - modeldb.data[0].toInt() + currentDay >= 7) {
                        mainViewModel.delete(modeldb)
                    }
                }
            }
        } catch (e:java.lang.Exception){
            e.localizedMessage?.let { Log.e("Log", it) }
        }
    }
}