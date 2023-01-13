package com.example.englishwords.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwords.models.retrofitModels.CompletedResult
import com.example.englishwords.models.retrofitModels.englishWordsModel.MainEnglishModel
import com.example.englishwords.repositorys.MainRepository
import com.example.englishwords.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private var _completedResult:MutableStateFlow<CompletedResult?> = MutableStateFlow(null)
    var completedResult:StateFlow<CompletedResult?> = _completedResult

    fun getWordDefinitionInstance(endPoint: String) {
        viewModelScope.launch {
            loading.value = true
            var data = async(Dispatchers.IO) {
                repository.getWordDefinition(endPoint)
            }.await()
            _completedResult.value = withContext(Dispatchers.Default) {
                if (data.isSuccessful) {
                    var word: String? = null
                    var definit: MutableList<String> = mutableListOf()
                    var instanc: MutableList<String> = mutableListOf()
                    data.body()!!.forEach { model ->
                        word = model.word
                        model.meanings.forEach { meaning ->
                            meaning.definitions.forEach { definition ->
                                definit.add(definition.definition)
                                if (definition.example != null)
                                    instanc.add(definition.example)
                            }
                        }
                    }
                    return@withContext CompletedResult(
                        isSuccess = true,
                        word = word,
                        definition = definit,
                        instance = instanc
                    )
                } else {
                    return@withContext CompletedResult(isSuccess = false,null,null,null)
                }
            }
            loading.value = false
        }
    }

    fun getWordDefinition(endPoint: String) {
        viewModelScope.launch {
            loading.value = true
            val result = repository.getWordDefinition(endPoint)
            if (result.isSuccessful) {
                _wordDefinition.value = result
                requestComplete.value = true
            } else {
                requestComplete.value = false
            }
            loading.value = false
        }
    }
}