package com.example.umc_flo

import androidx.room.*

@Entity(tableName = "SongTable")
data class Song (
    val title : String = "",
    val singer : String = "",
    var second : Int = 0,
    val playtime : Int = 0,
    var isPlaying : Boolean = false,
    var music: String = "",
    var coverImg : Int? = null,
    var isLike : Boolean = false,
    val albumIdx: Int = 0
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}