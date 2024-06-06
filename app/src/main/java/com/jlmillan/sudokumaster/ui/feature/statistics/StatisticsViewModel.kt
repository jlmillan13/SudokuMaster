package com.jlmillan.sudokumaster.ui.feature.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlmillan.sudokumaster.data.dto.Statistics
import com.jlmillan.sudokumaster.domain.feature.statistics.StatisticsRepository

class StatisticsViewModel : ViewModel() {

    private val repository = StatisticsRepository()

    private val statisticsLiveData = MutableLiveData<List<Statistics>>()
    val statistics: LiveData<List<Statistics>> get() = statisticsLiveData

    init {
        loadStatistics()
    }

    private fun loadStatistics() {
        repository.getStatistics { statisticsList ->
            statisticsLiveData.value = statisticsList
        }
    }
}
