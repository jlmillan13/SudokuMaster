package com.jlmillan.sudokumaster.ui.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlmillan.sudokumaster.data.auth.AuthRepositoryImpl
import com.jlmillan.sudokumaster.domain.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
class ProfileViewModel : ViewModel() {
    private val userMutableState = MutableStateFlow<UserModel?>(null)
    val userState = userMutableState.asStateFlow()
    private val loadingMutableState = MutableStateFlow<Boolean>(false)
    val loadingState = loadingMutableState.asStateFlow()

    fun loadUser() {
        flow<Unit> {
            loadingMutableState.value = true
            userMutableState.value = AuthRepositoryImpl.getUser()
            loadingMutableState.value = false
        }.flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}