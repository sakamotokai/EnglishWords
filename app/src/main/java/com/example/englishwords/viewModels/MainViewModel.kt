package com.example.englishwords.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwords.models.retrofitModels.englishWordsModel.MainEnglishModel
import com.example.englishwords.repositorys.MainRepository
import com.example.englishwords.retrofit.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val retrofitInstance: RetrofitInstance,
    private val repository: MainRepository
) :
    ViewModel() {

    var loading = mutableStateOf(false)
    var requestComplete = mutableStateOf(false)

    private var _wordDefinition: MutableStateFlow<Response<MainEnglishModel>?> =
        MutableStateFlow(null)
    var wordDefinition: StateFlow<Response<MainEnglishModel>?> = _wordDefinition

    fun getWordDefinition(endPoint: String) {
        viewModelScope.launch {
            loading.value = true
            val result = repository.getWordDefinition(endPoint)
            if(result.isSuccessful){
                _wordDefinition.value = result
                requestComplete.value = true
            } else{
                requestComplete.value = false
            }
            loading.value = false
        }
    }
}