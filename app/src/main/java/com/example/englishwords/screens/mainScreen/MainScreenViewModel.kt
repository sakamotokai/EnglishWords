package com.example.englishwords.screens.mainScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwords.repositorys.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(private val repository: Repository) :ViewModel(){
    var aboveCardState:MutableState<String?> = mutableStateOf(null)
    var saveState:String? = null

    /*private var _searchedWord: MutableStateFlow<String?> = MutableStateFlow(null)
    var searchedWord:StateFlow<String?> = _searchedWord*/

    private var _saveSearchedWord = mutableStateOf("")
    val searchedWord = _saveSearchedWord

    private var _moveButton = mutableStateOf(false)
    val moveButton = _moveButton

    fun setMoveButton(state:Boolean){
        _moveButton.value = state
    }

    fun setSearchedWord(word:String){
       _saveSearchedWord.value = word
    }

    suspend fun checkGoalWord(word:String):String{
            return repository.getGoalWord(word)
    }

}