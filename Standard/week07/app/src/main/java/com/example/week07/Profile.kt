package com.example.week07

import androidx.room.*

@Entity
data class Profile(
    var name: String,
    var age: String,
    var phone: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
