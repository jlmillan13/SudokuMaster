package com.jlmillan.sudokumaster.ui.feature.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class MainViewModel : ViewModel() {
    private val stateLoadingMutable = MutableStateFlow(false)
    val stateLoading: StateFlow<Boolean> = stateLoadingMutable.asStateFlow()


    fun setLoading(value: Boolean) {
        stateLoadingMutable.value = value
    }
}
