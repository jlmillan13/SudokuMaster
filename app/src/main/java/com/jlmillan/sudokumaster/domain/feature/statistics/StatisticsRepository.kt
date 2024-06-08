package com.jlmillan.sudokumaster.domain.feature.statistics

import com.jlmillan.sudokumaster.data.dto.GameProgress
import com.jlmillan.sudokumaster.data.dto.GameStats
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class StatisticsRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("games")

    suspend fun saveGameProgress(progress: GameProgress) {
        val key = progress.userId
        database.child("progress").child(key).setValue(progress).await()
    }

    suspend fun getGameProgress(userId: String): GameProgress? {
        val snapshot = database.child("progress").child(userId).get().await()
        return snapshot.getValue(GameProgress::class.java)
    }

    suspend fun saveGameStats(stats: GameStats) {
        val key = stats.userId
        database.child("stats").child(key).setValue(stats).await()
    }

    suspend fun getUserStats(userId: String): List<GameStats> {
        val snapshot = database.child("stats").child(userId).get().await()
        val statsList = mutableListOf<GameStats>()
        for (child in snapshot.children) {
            val stat = child.getValue(GameStats::class.java)
            if (stat != null) {
                statsList.add(stat)
            }
        }
        return statsList
    }
}
