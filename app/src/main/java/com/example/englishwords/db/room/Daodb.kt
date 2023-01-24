package com.example.englishwords.db.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Daodb {
    @Insert
    suspend fun insert(modeldb:Modeldb)
    @Delete
    suspend fun delete(modeldb: Modeldb)
    @Update
    suspend fun update(modeldb: Modeldb)
    @Query("SELECT*FROM wordKeeper")
    fun getAll(): Flow<List<Modeldb>>
    @Query("SELECT*FROM wordKeeper WHERE word LIKE :wordInstance")
    fun getGoalWord(wordInstance:String):List<Modeldb>
    @Query("DELETE FROM wordKeeper WHERE word LIKE :name")
    fun deleteByName(name:String)
    @Query("SELECT * FROM wordKeeper WHERE word LIKE :word")
    fun getDataByWord(word:String):Modeldb
}