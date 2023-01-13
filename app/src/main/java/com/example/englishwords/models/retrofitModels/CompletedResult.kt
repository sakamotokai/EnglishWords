package com.example.englishwords.models.retrofitModels

data class CompletedResult(
    val isSuccess:Boolean,
    val word:String?,
    val definition:List<String>?,
    val instance:List<String>?,
    val urlToListening:String?
)
