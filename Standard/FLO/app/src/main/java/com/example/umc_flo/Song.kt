package com.example.umc_flo

data class Song (
    val title : String = "",
    val singer : String = "",
    val second : Int = 0,
    val playtime : Int = 0,
    var isPlaying : Boolean = false
)