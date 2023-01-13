package com.example.englishwords.models.retrofitModels.englishWordsModel

data class Definition(
    val antonyms: List<Any>,
    val definition: String,
    val example: String?,
    val synonyms: List<Any>
)