package com.jlmillan.sudokumaster.data.auth

import android.util.Log
import com.jlmillan.sudokumaster.data.common.CacheManager
import com.jlmillan.sudokumaster.data.dto.UserDTO
import com.jlmillan.sudokumaster.data.dto.toModel
import com.jlmillan.sudokumaster.domain.model.AuthErrorException
import com.jlmillan.sudokumaster.domain.model.UserModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jlmillan.sudokumaster.ui.USER_COLLECTION
import com.jlmillan.sudokumaster.ui.common.extension.validateEmail
import kotlinx.coroutines.tasks.await

object AuthRemoteDataSource {

    private var auth: FirebaseAuth = Firebase.auth

    suspend fun register(password: String, email: String): Pair<Boolean, AuthErrorException?> {
        return try {
            val result: AuthResult = auth.createUserWithEmailAndPassword(email, password).await()
            result.user?.let { firebaseUser ->
                val user = UserDTO(
                    email = email,
                    id = firebaseUser.uid
                )
                Firebase.firestore
                    .collection(USER_COLLECTION)
                    .add(user.toMap())
                    .await()
                CacheManager.user = user.toModel()
            }
            if (result.user != null) {
                true to null
            } else {
                false to AuthErrorException.UNKNOWN
            }
        } catch (e: FirebaseAuthException) {
            Log.e(AuthRemoteDataSource::class.java.simpleName, "${e.message}")
            when(e) {
                is FirebaseAuthInvalidCredentialsException -> false to AuthErrorException.WRONG_PASSWORD
                is FirebaseAuthInvalidUserException,
                is FirebaseAuthUserCollisionException -> false to AuthErrorException.EMAIL_EXIST
                else -> false to AuthErrorException.UNKNOWN
            }
        } catch (e: Exception) {
            Log.e(AuthRemoteDataSource::class.java.simpleName, "${e.message}")
            false to AuthErrorException.UNKNOWN
        }
    }

    suspend fun login(username: String, password: String): Pair<Boolean, AuthErrorException?> {
        return try {
            val email = if (username.validateEmail()) {
                username
            } else {
                // GET EMAIL FROM USERNAME
                val snapshot = Firebase.firestore
                    .collection(USER_COLLECTION)
                    .whereEqualTo("username", username)
                    .get()
                    .await()
                val userList = snapshot.toObjects(UserDTO::class.java)
                userList.firstOrNull()?.email
            }
            if (email.isNullOrBlank().not()) {
                val result: AuthResult = auth.signInWithEmailAndPassword(email.orEmpty(), password).await()
                if (result.user != null) {
                    true to null
                } else {
                    false to AuthErrorException.UNKNOWN
                }
            } else {
                false to AuthErrorException.USERNAME_NOT_EXIST
            }
        } catch (e: FirebaseAuthException) {
            when(e) {
                is FirebaseAuthInvalidCredentialsException -> false to AuthErrorException.WRONG_PASSWORD
                is FirebaseAuthInvalidUserException -> false to AuthErrorException.EMAIL_EXIST
                else -> false to AuthErrorException.UNKNOWN
            }
        } catch (e: Exception) {
            Log.e(AuthRemoteDataSource::class.java.simpleName, "${e.message}")
            false to AuthErrorException.UNKNOWN
        }
    }

    suspend fun isLogged(): Boolean {
        CacheManager.user = getRemoteUser()
        return Firebase.auth.currentUser != null
    }

    suspend fun getUser(): UserModel {
        return getRemoteUser() ?: UserModel()
    }

    private suspend fun getRemoteUser() : UserModel? {
        val snapshot = Firebase.firestore
            .collection(USER_COLLECTION)
            .whereEqualTo("email", Firebase.auth.currentUser?.email)
            .get()
            .await()
        val userSnapshot = snapshot.documents.firstOrNull()
        val userDto = userSnapshot?.toObject(UserDTO::class.java)
        return userDto?.copy(hashId = userSnapshot.id)?.toModel()
    }

}