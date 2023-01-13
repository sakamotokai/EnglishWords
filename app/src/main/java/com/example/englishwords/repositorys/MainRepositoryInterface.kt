package com.example.englishwords.repositorys

import com.example.englishwords.models.retrofitModels.englishWordsModel.MainEnglishModel
import retrofit2.Response

interface MainRepositoryInterface {
    suspend fun getWordDefinition(endPoint:String):Response<MainEnglishModel>
}