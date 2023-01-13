package com.example.englishwords

sealed class SharedPreferencesEnum(var route:String){
    object settings:SharedPreferencesEnum("sharedPreferences")
}