package com.example.englishwords.viewModels


import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.englishwords.models.preferencesModels.SettingsSharedPreferences
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.parameter.parametersOf

class GlobalSettingsViewModel(private val sharedPreferencesModel: SettingsSharedPreferences):ViewModel() {
    init {
        Log.e("Log", "Setting Screen Create")
    }

    private var _mainDrawerGesturesEnabled:MutableStateFlow<Boolean> = MutableStateFlow(true)
    var mainDrawerGesturesEnabled:StateFlow<Boolean> = _mainDrawerGesturesEnabled

    private var sharedPreferences = sharedPreferencesModel.getPreferences()

    private var _ownStyle:MutableStateFlow<OwnTheme.OwnStyle> = MutableStateFlow(
        when (sharedPreferences.getString("ownStyle", OwnTheme.OwnStyle.Purple.name)) {
            "Purple" -> OwnTheme.OwnStyle.Purple
            "Blue" -> OwnTheme.OwnStyle.Blue
            "Red" -> OwnTheme.OwnStyle.Red
            "Green" -> OwnTheme.OwnStyle.Green
            "Black" -> OwnTheme.OwnStyle.Black
            "Custom" -> OwnTheme.OwnStyle.Custom
            else -> OwnTheme.OwnStyle.Purple
        }
    )
    var ownStyle:StateFlow<OwnTheme.OwnStyle> = _ownStyle

    private var _isDarkMode:MutableStateFlow<Boolean> = MutableStateFlow<Boolean>(sharedPreferences.getBoolean("isDarkmode",false))
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    fun changeOwnStyle(style:OwnTheme.OwnStyle){
        _ownStyle.value = style
        sharedPreferences.edit().putString("ownStyle",style.name).apply()
    }

    fun changeDarkMode(){
        _isDarkMode.value = !_isDarkMode.value
        sharedPreferences.edit().putBoolean("isDarkmode", _isDarkMode.value).apply()
    }

    fun setMainGesturesEnabled(state:Boolean){
        _mainDrawerGesturesEnabled.value = state
    }

    fun changeMainGesturesEnabled(){
        _mainDrawerGesturesEnabled.value = !_mainDrawerGesturesEnabled.value
    }
}