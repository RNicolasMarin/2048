package com.example.two_zero_four_eight.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class RecordValues(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val score: Int,
    val number: Int,
    val boardSize: Int
)
