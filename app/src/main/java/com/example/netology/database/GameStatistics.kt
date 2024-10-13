package com.example.netology.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_statistics")
data class GameStatistics(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val totalClicks: Int,
    val mouseClicks: Int,
    val hitPercentage: Float,
    val duration: Long
)