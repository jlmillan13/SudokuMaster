package com.jlmillan.sudokumaster.data.auth

import com.fesac.sudokumaster.data.common.CacheManager
import com.fesac.sudokumaster.domain.feature.auth.AuthRepository
import com.fesac.sudokumaster.domain.model.AuthErrorException
import com.fesac.sudokumaster.domain.model.UserModel

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

    override suspend fun markFavorite(id: String) {
        CacheManager.user = AuthRemoteDataSource.markFavorite(id)
    }

    override suspend fun editName(newUserName: String): Boolean {
        val response = AuthRemoteDataSource.editName(newUserName)
        CacheManager.user = response.first
        return response.second
    }
}