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
}