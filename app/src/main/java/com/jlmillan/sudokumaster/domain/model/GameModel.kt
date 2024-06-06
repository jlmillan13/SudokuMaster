package com.jlmillan.sudokumaster.domain.model

import com.jlmillan.sudokumaster.data.dto.GameProgress
import com.jlmillan.sudokumaster.data.dto.GameStats
import com.jlmillan.sudokumaster.domain.feature.statistics.StatisticsRepository

data class GameModel(
    val progress: GameProgress,
    val stats: GameStats
) {
    private val statisticsRepository = StatisticsRepository()

    suspend fun saveGameProgress() {
        statisticsRepository.saveGameProgress(progress)
    }

    suspend fun getGameProgress(userId: String): GameProgress? {
        return statisticsRepository.getGameProgress(userId)
    }

    suspend fun saveGameStats() {
        statisticsRepository.saveGameStats(stats)
    }

    suspend fun getUserStats(userId: String): List<GameStats> {
        return statisticsRepository.getUserStats(userId)
    }
}

