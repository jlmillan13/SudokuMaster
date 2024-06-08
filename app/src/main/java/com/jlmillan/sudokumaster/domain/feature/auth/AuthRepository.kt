package com.jlmillan.sudokumaster.domain.feature.auth

import com.jlmillan.sudokumaster.domain.model.AuthErrorException
import com.jlmillan.sudokumaster.domain.model.UserModel

interface AuthRepository {
    suspend fun register(password: String, email: String): Pair<Boolean, AuthErrorException?>
    suspend fun login(username: String, password: String): Pair<Boolean, AuthErrorException?>
    suspend fun isLogged(): Boolean
    suspend fun getUser(): UserModel

}