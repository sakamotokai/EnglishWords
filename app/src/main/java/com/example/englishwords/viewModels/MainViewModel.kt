package com.example.englishwords.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.models.retrofitModels.CompletedResult
import com.example.englishwords.repositorys.Repository
import com.example.englishwords.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.get

class MainViewModel(
    private val retrofitInstance: RetrofitInstance,
    private val repository: Repository
) :
    ViewModel() {

    var loading = mutableStateOf(false)

    private var _getAllRoomData:MutableStateFlow<List<Modeldb>?> = MutableStateFlow(null)
    var getAllRoomData:StateFlow<List<Modeldb>?> = _getAllRoomData

    private var _completedResult:MutableStateFlow<CompletedResult?> = MutableStateFlow(null)
    var completedResult:StateFlow<CompletedResult?> = _completedResult

    fun getCompletedResult(endPoint: String) {
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
                    var urlTo:String? = null
                    data.body()!!.forEach { model ->
                        word = model.word
                        model.phonetics.forEach { url->
                            urlTo = url.audio
                        }
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
                        instance = instanc,
                        urlToListening = urlTo
                    )
                } else {
                    return@withContext CompletedResult(isSuccess = false,null,null,null,null)
                }
            }
            loading.value = false
        }
    }
    fun insert(modeldb: Modeldb){
        viewModelScope.launch {
            repository.insert(modeldb)
        }
    }

    fun update(modeldb: Modeldb){
        viewModelScope.launch{
            repository.update(modeldb)
        }
    }

    fun delete(modeldb:Modeldb){
        viewModelScope.launch{
            repository.delete(modeldb)
        }
    }

    fun getAllFromRoom(){
        viewModelScope.launch{
            repository.getAll().collect{
                _getAllRoomData.value = it
            }
        }
    }
}