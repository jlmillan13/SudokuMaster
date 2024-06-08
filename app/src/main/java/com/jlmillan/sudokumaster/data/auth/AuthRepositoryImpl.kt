package com.jlmillan.sudokumaster.data.auth

import com.jlmillan.sudokumaster.domain.feature.auth.AuthRepository
import com.jlmillan.sudokumaster.domain.model.AuthErrorException
import com.jlmillan.sudokumaster.domain.model.UserModel

object AuthRepositoryImpl : AuthRepository {

    override suspend fun register(password: String,email: String): Pair<Boolean, AuthErrorException?> {
        return AuthRemoteDataSource.register(password, email)
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