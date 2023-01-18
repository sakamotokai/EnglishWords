package com.example.englishwords.viewModels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.englishwords.SharedPreferencesEnum
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get

class GlobalSettingsViewModel(val sharedPreferences:SharedPreferences):ViewModel() {
    init {
        Log.e("Log", "Setting Screen Create")
    }
   /* private val sharedPreferences = get<SharedPreferences>(parameters = { parametersOf(SharedPreferencesEnum.settings) }
    , clazz = SharedPreferences::class.java)*/

    private var _ownStyle:MutableStateFlow<OwnTheme.OwnStyle> = MutableStateFlow(
        when (sharedPreferences.getString("ownStyle", OwnTheme.OwnStyle.Purple.name)) {
            "Purple" -> OwnTheme.OwnStyle.Purple
            "Blue" -> OwnTheme.OwnStyle.Blue
            "Red" -> OwnTheme.OwnStyle.Red
            "Green" -> OwnTheme.OwnStyle.Green
            "Black" -> OwnTheme.OwnStyle.Black
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

}