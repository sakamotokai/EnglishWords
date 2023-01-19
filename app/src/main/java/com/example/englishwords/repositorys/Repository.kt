package com.example.englishwords.repositorys

import com.example.englishwords.db.room.Daodb
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.models.retrofitModels.englishWordsModel.MainEnglishModel
import com.example.englishwords.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

class Repository(val retrofitInstance: RetrofitInstance, val dao: Daodb) :
    NetworkRepositoryInterface, RoomRepositoryInterface {

    override suspend fun getWordDefinition(endPoint: String): Response<MainEnglishModel> {
        return RetrofitInstance.api.getPost(endPoint)
    }

    override suspend fun insert(modeldb: Modeldb) {
        dao.insert(modeldb)
    }

    override suspend fun delete(modeldb: Modeldb) {
        dao.delete(modeldb)
    }

    override suspend fun update(modeldb: Modeldb) {
        dao.update(modeldb)
    }

    override suspend fun getAll(): Flow<List<Modeldb>> {
        return dao.getAll()
    }

    override suspend fun deleteEmptyWord() {
        val emptyList = dao.getGoalWord("")
        emptyList.forEach {item->
            dao.delete(item)
        }
    }

    override suspend fun getGoalWord(goalWord:String): String {
        return if (dao.getGoalWord(goalWord).isEmpty()) "" else goalWord
    }

    override suspend fun deleteByName(name: String) {
        dao.deleteByName(name)
    }

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun insertOwn(modeldb: Modeldb) {
        scope.launch {
            dao.insert(modeldb = modeldb)
        }
    }

}