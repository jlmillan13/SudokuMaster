package com.jlmillan.sudokumaster.data.common

import android.content.Context
import com.jlmillan.sudokumaster.domain.model.UserModel

object CacheManager {
    var user: UserModel? = null

    private const val PREFS_NAME = "sudoku_prefs"
    private const val KEY_POINTS_PREFIX = "key_points_"
    private const val KEY_TIME_PREFIX = "key_time_"

    fun saveUser(context: Context, userModel: UserModel) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("key_username", userModel.username)
            putString("key_email", userModel.email)
            putString("key_id", userModel.id)
            putString("key_hash_id", userModel.hashId)
            apply()
        }
        user = userModel
    }

    fun getUser(context: Context): UserModel? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("key_username", null) ?: return null
        val email = sharedPreferences.getString("key_email", null) ?: return null
        val id = sharedPreferences.getString("key_id", null) ?: return null
        val hashId = sharedPreferences.getString("key_hash_id", null) ?: return null
        user = UserModel(username, email, id, hashId)
        return user
    }

    fun saveGameResultIfBetter(context: Context, points: Int, time: Int, difficulty: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentBestPoints = getPoints(context, difficulty)
        val currentBestTime = getTime(context, difficulty)

        if (points > currentBestPoints || (points == currentBestPoints && time < currentBestTime)) {
            with(sharedPreferences.edit()) {
                putInt(KEY_POINTS_PREFIX + difficulty, points)
                putInt(KEY_TIME_PREFIX + difficulty, time)
                apply()
            }
        }
    }

    fun getPoints(context: Context, difficulty: Int): Int {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_POINTS_PREFIX + difficulty, 0)
    }

    fun getTime(context: Context, difficulty: Int): Int {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_TIME_PREFIX + difficulty, Int.MAX_VALUE)
    }
}
