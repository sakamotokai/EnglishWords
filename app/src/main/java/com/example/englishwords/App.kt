package com.example.englishwords

import android.app.Application
import android.content.Context
import com.example.englishwords.db.room.Database
import com.example.englishwords.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(mainDi, viewModelsModule, networkModule, databaseModule, otherModule))
        }

    }
    fun initRoomDatabase(){
        Database.getInstance(applicationContext).getDao()
    }
}