package com.jlmillan.sudokumaster.data.common

import android.content.Context
import com.jlmillan.sudokumaster.domain.model.UserModel

object CacheManager {
    var user: UserModel? = null

    private const val PREFS_NAME = "sudoku_prefs"
    private const val KEY_NAME = "key_name"
    private const val KEY_USERNAME = "key_username"
    private const val KEY_EMAIL = "key_email"
    private const val KEY_ID = "key_id"
    private const val KEY_HASH_ID = "key_hash_id"

    fun saveUser(context: Context, userModel: UserModel) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(KEY_USERNAME, userModel.username)
            putString(KEY_EMAIL, userModel.email)
            putString(KEY_ID, userModel.id)
            putString(KEY_HASH_ID, userModel.hashId)
            apply()
        }
        user = userModel
    }

    fun getUser(context: Context): UserModel? {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(KEY_USERNAME, null) ?: return null
        val email = sharedPreferences.getString(KEY_EMAIL, null) ?: return null
        val id = sharedPreferences.getString(KEY_ID, null) ?: return null
        val hashId = sharedPreferences.getString(KEY_HASH_ID, null) ?: return null
        user = UserModel( username, email, id, hashId)
        return user
    }
}
