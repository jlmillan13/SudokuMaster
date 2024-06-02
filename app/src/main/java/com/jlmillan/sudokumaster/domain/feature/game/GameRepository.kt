package com.jlmillan.sudokumaster.domain.feature.game

interface GameRepository {
}

package com.example.sudoku

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class GameRepository {
    private val db = FirebaseFirestore.getInstance()
    private val statsCollection = db.collection("game_stats")
    private val progressCollection = db.collection("game_progress")

    suspend fun saveGameStats(stats: GameStats) {
        statsCollection.add(stats).await()
    }

    suspend fun getUserStats(userId: String): List<GameStats> {
        return statsCollection
            .whereEqualTo("userId", userId)
            .orderBy("completionDate", Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects(GameStats::class.java)
    }

    suspend fun saveGameProgress(progress: GameProgress) {
        progressCollection.document(progress.userId).set(progress).await()
    }

    suspend fun getGameProgress(userId: String): GameProgress? {
        val document = progressCollection.document(userId).get().await()
        return if (document.exists()) {
            document.toObject(GameProgress::class.java)
        } else {
            null
        }
    }
}
