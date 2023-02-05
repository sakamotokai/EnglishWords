package com.example.englishwords.screens.settingsScreen.notifications

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.englishwords.models.preferencesModels.MessageSharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotificationViewModel(settingsSharedPreferences: MessageSharedPreferences) : ViewModel() {
    val messageSharedPreference = settingsSharedPreferences.getPreferences()

    private var _isRemindedNotifications: MutableStateFlow<Boolean> = MutableStateFlow(
       messageSharedPreference.getBoolean(
            SharedPreferencesNotifications.RemindedNotifications.name,
            true
        )
    )
    var isRemindedNotifications: StateFlow<Boolean> = _isRemindedNotifications

    private var _isWordNotifications: MutableStateFlow<Boolean> = MutableStateFlow(
       messageSharedPreference.getBoolean(
            SharedPreferencesNotifications.WordNotifications.name,
            true
        )
    )
    var isWordNotifications: StateFlow<Boolean> = _isWordNotifications

    private var _notificationTimeOut: MutableStateFlow<Long> = MutableStateFlow(
        messageSharedPreference.getLong(
            SharedPreferencesNotifications.WordNotificationsTimeOut.name,
            10800000
        )
    )
    var notificationTimeOut: StateFlow<Long> = _notificationTimeOut

    fun setNotificationTimeOut(time:Long) {
        Log.e("Log","second")
        Log.e("Log",time.toString())
        _notificationTimeOut.value = time
        messageSharedPreference.edit()
            .putLong(SharedPreferencesNotifications.WordNotificationsTimeOut.name, time)
            .apply()
    }

    fun changeWordNotificationsState() {
        _isWordNotifications.value = !_isWordNotifications.value
        messageSharedPreference.edit()
            .putBoolean(SharedPreferencesNotifications.WordNotifications.name, _isWordNotifications.value)
            .apply()
    }

    fun setNotificationsState(state: Boolean) {
        _isRemindedNotifications.value = state
       messageSharedPreference.edit()
            .putBoolean(SharedPreferencesNotifications.RemindedNotifications.name, state)
            .apply()
    }

    fun changeRemindedNotificationsState() {
        _isRemindedNotifications.value = !_isRemindedNotifications.value
       messageSharedPreference.edit()
            .putBoolean(SharedPreferencesNotifications.RemindedNotifications.name, _isRemindedNotifications.value)
            .apply()
    }

}