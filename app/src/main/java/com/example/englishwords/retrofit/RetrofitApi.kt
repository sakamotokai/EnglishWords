package com.example.englishwords.retrofit

import com.example.englishwords.models.retrofitModels.englishWordsModel.MainEnglishModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApi {
    @GET("{Path}")
suspend fun getPost(
        @Path("Path") endPoint:String
):Response<MainEnglishModel>
}
/*

interface ApiModel{
    @GET("{Path}")
    suspend fun getPost(
        @Path("Path") endPoint: String
    )
}

interface RetrofitApiForTest {
    @GET("{Path}")
    suspend fun getPost(
        @Path("Path") endPoint:String
    ):Response<MainEnglishModel>
}*/
