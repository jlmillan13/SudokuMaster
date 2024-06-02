package com.jlmillan.sudokumaster.data.dto

data class GameStats(
    val userId: String = "",
    val difficulty: String = "",
    val timeTaken: Long = 0L,
    val completionDate: Long = System.currentTimeMillis()
)
