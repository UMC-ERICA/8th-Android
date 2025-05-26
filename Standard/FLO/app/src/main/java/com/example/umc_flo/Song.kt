package com.example.umc_flo

import android.text.BoringLayout
import android.widget.Switch
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
    var isSwitchOn: Boolean = false
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}