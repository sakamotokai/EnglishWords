package com.example.englishwords.di

import android.content.Context
import com.example.englishwords.SharedPreferencesEnum
import com.example.englishwords.db.room.Database
import com.example.englishwords.models.preferencesModels.SettingsSharedPreferences
import com.example.englishwords.repositorys.Repository
import com.example.englishwords.retrofit.RetrofitInstance
import com.example.englishwords.screens.mainScreen.MainScreenViewModel
import com.example.englishwords.screens.settingsScreen.SettingsViewModel
import com.example.englishwords.screens.wordKeepedScreen.WordKeepedViewModel
import com.example.englishwords.viewModels.DrawerViewModel
import com.example.englishwords.viewModels.GlobalSettingsViewModel
import com.example.englishwords.viewModels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

var mainDi = module {

    single {
        val context: Context = get()
        context.getSharedPreferences(
            SharedPreferencesEnum.settings.route,
            Context.MODE_PRIVATE
        )
    }
    singleOf(::SettingsSharedPreferences)
}

var databaseModule = module{
    single{
        Database.getInstance(get()).getDao()
    }
}

var viewModelsModule = module{
    viewModel<MainViewModel> {
        MainViewModel(get(), get())
    }
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::WordKeepedViewModel)
    //viewModelOf(::GlobalSettingsViewModel)
    /*viewModel{
        GlobalSettingsViewModel(get(parameters = { parametersOf(SharedPreferencesEnum.settings.route) }))
    }*/
    single {
        DrawerViewModel()
    }
    viewModelOf(::SettingsViewModel)
    single{
        GlobalSettingsViewModel(get())
    }
}

var networkModule = module{
    single {
        RetrofitInstance
    }
    single {
        Repository(get(),get())
    }
    /*singleOf(::BaseRetrofitBuilder)
    singleOf(::BaseRetrofitInstance)*/
}