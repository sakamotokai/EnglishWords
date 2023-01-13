package com.example.englishwords.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/en/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RetrofitApi by lazy {
        retrofit.create(RetrofitApi::class.java)
    }
}