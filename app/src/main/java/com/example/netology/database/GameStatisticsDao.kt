package com.example.netology.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameStatisticsDao {
    @Insert
    suspend fun insert(statistics: GameStatistics)

    @Query("SELECT * FROM game_statistics ORDER BY id DESC LIMIT 10")
    suspend fun getLast10Games(): List<GameStatistics>
}