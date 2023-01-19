package com.example.englishwords.repositorys

import com.example.englishwords.db.room.Modeldb
import kotlinx.coroutines.flow.Flow

interface RoomRepositoryInterface {
    suspend fun insert(modeldb: Modeldb)
    suspend fun delete(modeldb: Modeldb)
    suspend fun update(modeldb: Modeldb)
    suspend fun getAll(): Flow<List<Modeldb>>
    suspend fun deleteEmptyWord()
    suspend fun getGoalWord(goalWord:String):String
    suspend fun deleteByName(name:String)
}