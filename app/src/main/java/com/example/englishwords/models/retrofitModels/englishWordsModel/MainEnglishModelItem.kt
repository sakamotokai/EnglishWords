package com.example.englishwords.models.retrofitModels.englishWordsModel

data class MainEnglishModelItem(
    val license: License,
    val meanings: List<Meaning>,
    val phonetics: List<Phonetic>,
    val sourceUrls: List<String>,
    val word: String
)