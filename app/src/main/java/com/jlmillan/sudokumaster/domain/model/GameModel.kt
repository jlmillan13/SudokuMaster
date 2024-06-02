package com.jlmillan.sudokumaster.domain.model

data class GameModel(
    val progress: GameProgress,
    val stats: GameStats
) {
    private val gameRepository = GameRepository()

    suspend fun saveGameProgress() {
        gameRepository.saveGameProgress(progress)
    }

    suspend fun getGameProgress(userId: String): GameProgress? {
        return gameRepository.getGameProgress(userId)
    }

    suspend fun saveGameStats() {
        gameRepository.saveGameStats(stats)
    }

    suspend fun getUserStats(userId: String): List<GameStats> {
        return gameRepository.getUserStats(userId)
    }
}

