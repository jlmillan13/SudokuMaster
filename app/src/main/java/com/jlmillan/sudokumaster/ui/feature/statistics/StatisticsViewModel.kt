package com.jlmillan.sudokumaster.ui.feature.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlmillan.sudokumaster.data.dto.GameStats
import com.jlmillan.sudokumaster.domain.feature.statistics.StatisticsRepository
import kotlinx.coroutines.launch

class StatisticsViewModel : ViewModel() {

    private val repository = StatisticsRepository()

    private val userStatsLiveData = MutableLiveData<List<GameStats>>()
    val userStats: LiveData<List<GameStats>> get() = userStatsLiveData

    fun loadUserStats(userId: String) {
        viewModelScope.launch {
            try {
                val stats = repository.getUserStats(userId)
                userStatsLiveData.value = stats
            } catch (e: Exception) {
                // Handle the exception, e.g., log it or show an error message to the user
                userStatsLiveData.value = emptyList()
            }
        }
    }
}
