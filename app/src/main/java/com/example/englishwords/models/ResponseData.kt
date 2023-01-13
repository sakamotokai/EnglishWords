package com.example.englishwords.models

data class ResponseData(
    var word:String?,
    var descriptions:MutableList<String>?,
    var instances:MutableList<String>?,
)
