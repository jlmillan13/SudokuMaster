package com.jlmillan.sudokumaster.data.auth

import com.jlmillan.sudokumaster.data.common.CacheManager
import com.jlmillan.sudokumaster.domain.feature.auth.AuthRepository
import com.jlmillan.sudokumaster.domain.model.AuthErrorException
import com.jlmillan.sudokumaster.domain.model.UserModel

object AuthRepositoryImpl : AuthRepository {
    override suspend fun register(username: String, password: String, email: String, name: String): Pair<Boolean, AuthErrorException?> {
        return AuthRemoteDataSource.register(username, password, email, name)
    }

    override suspend fun login(username: String, password: String): Pair<Boolean, AuthErrorException?> {
        return AuthRemoteDataSource.login(username, password)
    }

    override suspend fun isLogged(): Boolean {
        return AuthRemoteDataSource.isLogged()
    }

    override suspend fun getUser(): UserModel {
        return AuthRemoteDataSource.getUser()
    }
    
}