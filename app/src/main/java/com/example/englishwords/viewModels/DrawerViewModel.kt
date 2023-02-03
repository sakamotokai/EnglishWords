package com.example.englishwords.viewModels

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.MutableState
import androidx.lifecycle.Lifecycle.State
import androidx.lifecycle.ViewModel
import com.example.englishwords.db.room.Modeldb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow

class DrawerViewModel:ViewModel() {

    private var _drawerState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var drawerState: StateFlow<Boolean> = _drawerState

    fun closeDrawer(){
        _drawerState.value = false
    }

    fun setDrawerState(state:Boolean){
        _drawerState.value = state
    }

}