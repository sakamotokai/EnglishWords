package com.example.englishwords.viewModels

import androidx.lifecycle.ViewModel
import com.example.englishwords.models.preferencesModels.MessageSharedPreferences
import com.example.englishwords.models.preferencesModels.SettingsSharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime

class MessageViewModel(private val sharedPreferencesModel: MessageSharedPreferences) : ViewModel() {

    private var sharedPreferences = sharedPreferencesModel.getPreferences()
    private var localDate = LocalDate.now()
    private var localTime = LocalTime.now()

    private var _isAppOn: MutableStateFlow<Boolean> =
        MutableStateFlow<Boolean>(sharedPreferences.getBoolean("isAppOn", true))
    val isAppOn: StateFlow<Boolean> = _isAppOn

    fun setAppState(onOrOff: Boolean) {
        _isAppOn.value = onOrOff
        sharedPreferences.edit().putBoolean("isAppOn", onOrOff).apply()
    }

    private var _lastStartHour: MutableStateFlow<Int> = MutableStateFlow(
        sharedPreferences.getInt(
            "lastStartHour",
                LocalTime.now().hour
        )
    )
    val lastStartHour: StateFlow<Int> = _lastStartHour

    fun setLastHour(hour:Int) {
        _lastStartHour.value = hour
        sharedPreferences.edit().putInt("lastStartHour", hour).apply()
    }

}