package com.example.umc_flo

import androidx.room.*

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey val id: Int,
    val title: String,
    val singer: String,
    val coverImg: Int
)
