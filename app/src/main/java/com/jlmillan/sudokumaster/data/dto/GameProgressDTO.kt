package com.jlmillan.sudokumaster.data.dto

data class GameProgress(
    val userId: String = "",
    val puzzle: List<List<Int>> = listOf(),
    val selectedRow: Int = -1,
    val selectedCol: Int = -1,
    val timeSpent: Long = 0L
)
