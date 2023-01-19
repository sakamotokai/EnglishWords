package com.example.englishwords.models.preferencesModels

import android.content.Context
import android.content.SharedPreferences
import com.example.englishwords.SharedPreferencesEnum

interface BasePreferencesModel{
    val context:Context
    fun getPreferences():SharedPreferences
}

class SettingsSharedPreferences(override val context: Context):BasePreferencesModel {
    override fun getPreferences():SharedPreferences {
        return context.getSharedPreferences(
            SharedPreferencesEnum.settings.route,
            Context.MODE_PRIVATE
        )
    }
}
