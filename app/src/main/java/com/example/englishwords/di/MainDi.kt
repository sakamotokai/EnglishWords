package com.example.englishwords.di

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.englishwords.MainActivity
import com.example.englishwords.repositorys.MainRepository
import com.example.englishwords.retrofit.RetrofitInstance
import com.example.englishwords.viewModels.MainViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import org.koin.dsl.module

var mainDi = module {
    single {
        RetrofitInstance
    }

    single {
        MainRepository(get())
    }

    viewModel<MainViewModel> {
        MainViewModel(get(), get())
    }

    single { param->
        val context: Context = get()
        context.getSharedPreferences(
            param.get(),
            Context.MODE_PRIVATE
        )
    }

}