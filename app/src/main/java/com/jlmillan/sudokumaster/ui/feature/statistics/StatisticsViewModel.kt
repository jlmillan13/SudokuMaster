package com.jlmillan.sudokumaster.ui.feature.statistics

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.jlmillan.sudokumaster.data.dto.Statistics
import kotlinx.coroutines.launch

class StatisticsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val userId = "user_unique_id" // Esto debe ser dinámico según el usuario autenticado

    val statistics = mutableStateOf(Statistics())

    fun loadStatistics(difficulty: String) {
        viewModelScope.launch {
            val docRef = db.collection("users").document(userId).collection("statistics").document(difficulty)
            docRef.get().addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val stats = document.toObject(Statistics::class.java)
                    statistics.value = stats ?: Statistics()
                }
            }
        }
    }

    fun saveStatistics(difficulty: String, newScore: Int, newTime: Long) {
        viewModelScope.launch {
            val currentStats = statistics.value
            val updatedStats = Statistics(
                bestScore = maxOf(currentStats.bestScore, newScore),
                bestTime = if (currentStats.bestTime == 0L) newTime else minOf(currentStats.bestTime, newTime)
            )
            db.collection("users").document(userId).collection("statistics").document(difficulty)
                .set(updatedStats)
            statistics.value = updatedStats
        }
    }
}
