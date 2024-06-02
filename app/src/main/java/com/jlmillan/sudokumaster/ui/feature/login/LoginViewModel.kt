package com.jlmillan.sudokumaster.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlmillan.sudokumaster.data.auth.AuthRepositoryImpl
import com.jlmillan.sudokumaster.domain.model.AuthErrorException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class LoginViewModel : ViewModel() {
    private val stateSuccessMutable = MutableStateFlow<Pair<Boolean, AuthErrorException?>>(false to null)
    val stateSuccess: StateFlow<Pair<Boolean, AuthErrorException?>> = stateSuccessMutable.asStateFlow()

    private val stateLoadingMutable = MutableStateFlow(false)
    val stateLoading: StateFlow<Boolean> = stateLoadingMutable.asStateFlow()

    fun login(username: String, password: String) {
        flow<Unit> {
            val response = AuthRepositoryImpl.login(username, password)
            stateSuccessMutable.value = response
        }.flowOn(Dispatchers.IO)
            .onStart {
                stateLoadingMutable.value = true
            }.onCompletion {
                stateLoadingMutable.value = false
            }.launchIn(viewModelScope)
    }
}