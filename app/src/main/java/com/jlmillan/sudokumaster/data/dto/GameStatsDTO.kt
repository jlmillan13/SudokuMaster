package com.jlmillan.sudokumaster.data.dto

data class GameStats(
    val userId: String = "",
    val score: Int = 0,
    val timeTaken: Long = 0L,
    val completionDate: Long = System.currentTimeMillis()
)
