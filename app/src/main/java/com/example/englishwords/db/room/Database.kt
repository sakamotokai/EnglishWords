package com.example.englishwords.db.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import okhttp3.internal.notify

@androidx.room.Database(entities = [Modeldb::class], version = 2, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun getDao(): Daodb

    companion object {
        private var database: Database? = null

        @Synchronized
        fun getInstance(context: Context): Database {
            return if (database == null) {
                database = Room.databaseBuilder(context, Database::class.java, "db5")
                    .fallbackToDestructiveMigration().build()
                database as Database
            } else {
                database as Database
            }
        }
    }
}
