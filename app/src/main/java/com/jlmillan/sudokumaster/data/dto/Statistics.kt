package com.jlmillan.sudokumaster.data.dto

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Statistics(
    val username: String? = "",
    val score: Int = 0,
    val time: Long = 0L
)