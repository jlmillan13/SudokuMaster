package com.jlmillan.sudokumaster.domain.feature.statistics


import com.jlmillan.sudokumaster.data.dto.Statistics
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference

class StatisticsRepository {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("userStats")

    fun getStatistics(callback: (List<Statistics>) -> Unit) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val statisticsList = mutableListOf<Statistics>()
                for (snapshot in dataSnapshot.children) {
                    val statistics = snapshot.getValue(Statistics::class.java)
                    statistics?.let { statisticsList.add(it) }
                }
                callback(statisticsList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
            }
        })
    }

    fun saveUserStats(statistics: Statistics) {
        val key = database.push().key
        key?.let {
            database.child(it).setValue(statistics)
        }
    }
}


