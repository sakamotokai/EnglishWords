package com.example.englishwords.models.retrofitModels

import com.example.englishwords.models.retrofitModels.englishWordsModel.MainEnglishModel

data class ResponseHandler(
    val completed: MainEnglishModel,
    val status: Boolean
)