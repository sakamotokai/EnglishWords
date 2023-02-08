package com.example.englishwords.screens.mainScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.repositorys.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainScreenViewModel(private val repository: Repository) :ViewModel(){
    var aboveCardState:MutableState<String?> = mutableStateOf(null)

    private var _saveSearchedWord = mutableStateOf("")
    val searchedWord = _saveSearchedWord

    private var _moveButton = mutableStateOf(false)
    val moveButton = _moveButton

    private var _errorCLickOnButton = mutableStateOf(false)
    val errorCLickOnButton = _errorCLickOnButton

    private var _saveNote:MutableStateFlow<Pair<Modeldb,String>?> = MutableStateFlow(null)
    val saveNote:StateFlow<Pair<Modeldb,String>?> = _saveNote

    private var _viewNoteCreateState:MutableStateFlow<Boolean> = MutableStateFlow(false)
    val viewNoteCreateState:StateFlow<Boolean> = _viewNoteCreateState

    fun setViewNoteCreateState(state:Boolean){
        _viewNoteCreateState.value = state
    }

    fun setSaveNote(pair:Pair<Modeldb,String>?){
        _saveNote.value = pair
    }

    fun setErrorClickOnButton(state:Boolean){
        _errorCLickOnButton.value = state
    }

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