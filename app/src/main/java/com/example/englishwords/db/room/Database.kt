package com.example.englishwords.db.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter

@androidx.room.Database(entities = [Modeldb::class],version  = 1)
abstract class Database: RoomDatabase(){
    abstract fun getDao():Daodb
    companion object{
        private var database:Database?= null
        @Synchronized
        fun getInstance(context: Context):Database{
            return if(database == null){
                database = Room.databaseBuilder(context,Database::class.java,"db1").build()
                database as Database
            } else {
                database as Database
            }
        }
    }
}
