package com.example.englishwords.models.retrofitModels.englishWordsModel

data class Meaning(
    val antonyms: List<String>,
    val definitions: List<Definition>,
    val partOfSpeech: String,
    val synonyms: List<String>
)