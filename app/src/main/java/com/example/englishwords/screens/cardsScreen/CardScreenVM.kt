package com.example.englishwords.screens.cardsScreen

import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.ui.theme.ownTheme.OwnTheme
import kotlinx.coroutines.launch

class CardScreenVM:ViewModel() {

    var list = mutableStateListOf<Modeldb>()

    fun getSimilarWords(modeldb: Modeldb):String{
        var localSimilarWordContainer by mutableStateOf("")
        modeldb.similar.forEach {
            localSimilarWordContainer += it
            localSimilarWordContainer += ", "
        }
        return localSimilarWordContainer
    }

}