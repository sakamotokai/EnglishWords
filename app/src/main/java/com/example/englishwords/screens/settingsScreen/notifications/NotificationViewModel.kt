package com.example.englishwords.screens.settingsScreen.notifications

import androidx.lifecycle.ViewModel
import com.example.englishwords.models.preferencesModels.SettingsSharedPreferences
import com.example.englishwords.ui.theme.ownTheme.CustomColorName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotificationViewModel(settingsSharedPreferences: SettingsSharedPreferences) : ViewModel() {
    val settingsSharedPreference = settingsSharedPreferences.getPreferences()
    private var _isNotifications: MutableStateFlow<Boolean> = MutableStateFlow(
        settingsSharedPreference.getBoolean(
            SharedPreferencesNotifications.SharedPreferenceNotifications.name,
            true
        )
    )
    var isNotifications: StateFlow<Boolean> = _isNotifications

    fun setNotificationsState(state: Boolean) {
        _isNotifications.value = state
        settingsSharedPreference.edit()
            .putBoolean(SharedPreferencesNotifications.SharedPreferenceNotifications.name, state)
            .apply()
    }

    fun changeNotificationsState() {
        _isNotifications.value = !_isNotifications.value
        settingsSharedPreference.edit()
            .putBoolean(SharedPreferencesNotifications.SharedPreferenceNotifications.name, _isNotifications.value)
            .apply()
    }

}