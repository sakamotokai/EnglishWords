package com.example.englishwords.screens.mainScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainScreenViewModel :ViewModel(){
    var aboveCardState:MutableState<String?> = mutableStateOf(null)
    var saveState:String? = null
}