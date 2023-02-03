package com.example.englishwords.transfer

import androidx.room.TypeConverter
import com.example.englishwords.db.room.Modeldb
import com.example.englishwords.db.room.ModeldbWorkerClass
import kotlinx.coroutines.*
import java.util.*
import java.util.stream.Collectors

class HobbiesConverter {
    @TypeConverter
    fun fromExamples(examples: List<String?>): String {
        var string:String = ""
        examples.forEach {
            string += it
            string += "]!["
        }
        return string//examples.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toExample(data: String): List<String> {
        return data.split("]!\\[".toRegex()).dropLastWhile { it.isEmpty() }
        /*return listOf(Arrays.asList(data.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()).toString())*/
    }
}

