package com.example.englishwords.db.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.englishwords.transfer.HobbiesConverter
import org.jetbrains.annotations.PropertyKey

interface ModelRoomDatabase {
    var id: Int
    var word: String
    var linkToSound: String
    var definitions: List<String>
    var examples: List<String>
    var note:String
    var data:List<String>
    var similar:List<String>
    var urlToImage:String
}

interface ModelWorkedRoomDatabase{
    var id:Int
    var word: String
    var linkToSound: String
    var definitions: MutableList<String>
    var examples: MutableList<String>
    var note:String
    var data:MutableList<String>
}

data class ModeldbWorkerClass(
    override var id: Int,
    override var word: String,
    override var linkToSound: String,
    override var definitions: MutableList<String>,
    override var examples: MutableList<String>,
    override var note: String,
    override var data: MutableList<String>
):ModelWorkedRoomDatabase

@Entity(tableName = "wordKeeper")
@TypeConverters(HobbiesConverter::class)
class Modeldb(
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0,
    @ColumnInfo
    override var word: String = "",
    override var linkToSound: String = "",
    override var definitions: List<String> = listOf(),
    override var examples: List<String> = listOf(),
    override var note: String = "",
    override var data: List<String> = listOf(),
    override var similar: List<String> = listOf(),
    override var urlToImage: String = ""
) : ModelRoomDatabase