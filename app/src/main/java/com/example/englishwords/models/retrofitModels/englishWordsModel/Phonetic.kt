package com.example.englishwords.models.retrofitModels.englishWordsModel

data class Phonetic(
    val audio: String?,
    val license: License,
    val sourceUrl: String,
    val text: String
)