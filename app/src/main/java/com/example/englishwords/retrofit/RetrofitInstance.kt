package com.example.englishwords.retrofit

import com.example.englishwords.models.retrofitModels.englishWordsModel.MainEnglishModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

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
//--------------------------------------------
/*
interface BaseRetrofitBuilderInterface {
    val retrofit: Retrofit
}

class BaseRetrofitBuilder(private val baseUrl: String = "https://api.dictionaryapi.dev/api/v2/entries/en/") :
    BaseRetrofitBuilderInterface {
    override val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

class Implementation(){
    interface RetrofitApi {
        @GET("{Path}")
        suspend fun getPost(
            @Path("Path") endPoint:String
        ): Response<MainEnglishModel>
    }

    class retrofitApi{
        @GET("{Path}")
        suspend fun getPost(
            @Path("Path") endPoint:String
        ): Response<MainEnglishModel>
    }

    interface RetrofitApiForTest {
        @GET("{Path}")
        suspend fun getPost(
            @Path("Path") endPoint:String
        ): Response<MainEnglishModel>
    }
}

interface BaseRetrofitInstanceInterface{
    val baseRetrofitBuilder:BaseRetrofitBuilderInterface
    val api:Implementation
}

class BaseRetrofitInstance(
    override val baseRetrofitBuilder: BaseRetrofitBuilderInterface, override val api: Implementation
):BaseRetrofitInstanceInterface{
    val own by lazy {

    }
}

class BaseRetrofitInstance(private val baseRetrofitBuilder: BaseRetrofitBuilderInterface) {
    val api: RetrofitApi by lazy {
        baseRetrofitBuilder.retrofit.create(RetrofitApi::class.java)
    }
}
 */