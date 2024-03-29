package com.example.englishwords.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.models.retrofitModels.CompletedResult
import com.example.englishwords.repositorys.Repository
import com.example.englishwords.retrofit.RetrofitInstance
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.get

class MainViewModel(
    private val retrofitInstance: RetrofitInstance,
    private val repository: Repository
) :
    ViewModel() {

    var loading = mutableStateOf(false)

    private var _getAllRoomData:MutableStateFlow<List<Modeldb>?> = MutableStateFlow(null)
    var getAllRoomData:StateFlow<List<Modeldb>?> = _getAllRoomData
    private var _getSingleRoomData:MutableStateFlow<Modeldb?> = MutableStateFlow(null)
    var getSingleRoomData:StateFlow<Modeldb?> = _getSingleRoomData

    private var _completedResult:MutableStateFlow<CompletedResult?> = MutableStateFlow(null)
    var completedResult:StateFlow<CompletedResult?> = _completedResult

    private var _cardList:MutableStateFlow<List<Modeldb>?> = MutableStateFlow(null)
    var cardList:StateFlow<List<Modeldb>?> = _cardList

    private var _goalWord:MutableStateFlow<String?> = MutableStateFlow(null)
    var goalWord:StateFlow<String?> = _goalWord

    fun getCompletedResult(endPoint: String) {
        viewModelScope.launch {
            try{
                loading.value = true
                var data = async(Dispatchers.IO) {
                    repository.getWordDefinition(endPoint)
                }.await()
                Log.e("Log","after")
                if(data == null) throw java.lang.Exception("Error of server page")
                Log.e("Log","after ${data.isSuccessful}")
                _completedResult.value = withContext(Dispatchers.Default) {
                    if (data.isSuccessful) {
                        var word: String? = null
                        val definit: MutableList<String> = mutableListOf()
                        val instanc: MutableList<String> = mutableListOf()
                        val similar:MutableList<String> = mutableListOf()
                        var urlTo: String? = null
                        data.body()!!.forEach { model ->
                            word = model.word
                            model.phonetics.forEach { url ->
                                urlTo = url.audio
                            }
                            //similar.addAll(model.similar)
                            model.meanings.forEach { meaning ->
                                similar.addAll(meaning.synonyms)
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
                            urlToListening = urlTo,
                            similar = similar
                        )
                    } else {
                        return@withContext CompletedResult(
                            isSuccess = false,
                            null,
                            null,
                            null,
                            null,
                            null
                        )
                    }
                }
                }catch (e:java.lang.Exception){
                    Log.e("Log","ERROR: ${e.message}")
                    Log.e("Log","ERROR: ${e.localizedMessage}")
                    Log.e("Log","ERROR: ${e.printStackTrace()}")
                    Log.e("Log","ERROR: ${e.fillInStackTrace()}")
                    Log.e("Log","ERROR: ${e.suppressedExceptions}")
                _completedResult.value = null/*CompletedResult(
                    isSuccess = false,
                    null,
                    null,
                    null,
                    null
                )*/
                } finally {
                    Log.e("Log","finally")
                loading.value = false
            }
        }
    }

    fun getCardList(){
        viewModelScope.launch {
            val list:MutableList<Modeldb> = mutableListOf()
            _getAllRoomData.value?.forEach {
                if (it.isUsingInCard)
                    list.add(it)
            }
            _cardList.value = list
        }
    }
    fun insert(modeldb: Modeldb){
        viewModelScope.launch (Dispatchers.Default){
            repository.insert(modeldb)
        }
    }

    fun update(modeldb: Modeldb){
        viewModelScope.launch(Dispatchers.Default){
            repository.update(modeldb)
        }
    }

    fun delete(modeldb:Modeldb){
        viewModelScope.launch(Dispatchers.Default){
            repository.delete(modeldb)
        }
    }

    fun getAllFromRoom(){
        viewModelScope.launch(Dispatchers.Default){
            repository.getAll().collect{
                _getAllRoomData.value = it
            }
        }
    }

    fun getRoomDataByWord(word:String){
        viewModelScope.launch(Dispatchers.Default) {
            _getSingleRoomData.value = repository.getDataByWord(word)
        }
    }

    fun deleteEmptyWordFromRoom(){
        viewModelScope.launch(Dispatchers.Default) {
            repository.deleteEmptyWord()
        }
    }

    fun getGoalWord(goalWord:String){
        viewModelScope.launch(Dispatchers.Default){
            _goalWord.value = repository.getGoalWord(goalWord)
        }
    }

    fun deleteByName(name:String){
        viewModelScope.launch(Dispatchers.Default) {
            repository.deleteByName(name)
        }
    }
}