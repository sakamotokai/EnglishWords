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
        return data.split("]![")
        /*return listOf(Arrays.asList(data.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()).toString())*/
    }
}


/*
suspend fun transferModeldbToWordedEdition(modeldb: Modeldb): ModeldbWorkerClass {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    val description: String = modeldb.definitions
    val examples: String = modeldb.examples
    var modeldbWorkerClass: ModeldbWorkerClass = ModeldbWorkerClass(
        modeldb.id, word = modeldb.word, linkToSound = modeldb.linkToSound,
        mutableListOf(),
        mutableListOf()
    )
    scope.launch {
        var word:String = ""
        val definitionList = mutableListOf<String>()
        for(char in modeldb.definitions){
            if(char != ' ' && char != modeldb.definitions[modeldb.definitions.length-1]) word += char
            else {
                definitionList.add(word)
                word = ""
            }
        }
        modeldbWorkerClass.definitions = definitionList
    }.join()
    scope.launch {
        var word:String = ""
        val exampleList = mutableListOf<String>()
        for(char in modeldb.definitions){
            if(char != ' ' && char != modeldb.examples[modeldb.examples.length-1]) word += char
            else {
                exampleList.add(word)
                word = ""
            }
        }
        modeldbWorkerClass.examples = exampleList
    }.join()
    return modeldbWorkerClass
}

suspend fun transferModeldbWorkedToModeldb(modeldbWorkerClass: ModeldbWorkerClass): Modeldb {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    val listDefinitions: List<String> = modeldbWorkerClass.definitions
    val listExamples: List<String> = modeldbWorkerClass.examples
    var modeldb: Modeldb =
        Modeldb(id = modeldbWorkerClass.id, modeldbWorkerClass.word, modeldbWorkerClass.linkToSound)
    scope.launch {
        listDefinitions.forEach {
            modeldb.definitions += it
            modeldb.definitions += " "
        }
    }.join()
    scope.launch {
        listExamples.forEach {
            modeldb.examples += it
            modeldb.examples += " "
        }
    }.join()
    return modeldb
}
*/

