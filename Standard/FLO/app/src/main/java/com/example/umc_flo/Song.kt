package com.example.umc_flo

data class Song (
    val title : String = "",
    val singer : String = "",
    var second : Int = 0,
    val playtime : Int = 0,
    var isPlaying : Boolean = false,
    var music: String = "",
    var coverImg : Int? = null,
    var isSwitchOn : Boolean = false
    )