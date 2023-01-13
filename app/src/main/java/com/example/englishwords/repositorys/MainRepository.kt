package com.example.englishwords.repositorys

import com.example.englishwords.models.retrofitModels.englishWordsModel.MainEnglishModel
import com.example.englishwords.retrofit.RetrofitInstance
import retrofit2.Response

class MainRepository(val retrofitInstance:RetrofitInstance):MainRepositoryInterface{

    override suspend fun getWordDefinition(endPoint: String): Response<MainEnglishModel> {
        return RetrofitInstance.api.getPost(endPoint)
    }

}