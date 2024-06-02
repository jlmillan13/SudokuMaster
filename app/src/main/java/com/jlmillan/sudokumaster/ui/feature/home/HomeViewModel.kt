package com.jlmillan.sudokumaster.ui.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlmillan.sudokumaster.data.auth.AuthRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart

class HomeViewModel : ViewModel() {
    private val loggedMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }
    private val loadingMutableLiveData by lazy {
        MutableLiveData<Boolean>()
    }

    fun loggedLiveData() : LiveData<Boolean> = loggedMutableLiveData
    fun loadingLiveData() : LiveData<Boolean> = loadingMutableLiveData

    init {
        isLogged()
    }

    private fun isLogged() {
        flow<Unit> {
            val isLogged = AuthRepositoryImpl.isLogged()
            loadingMutableLiveData.postValue(false)
            loggedMutableLiveData.postValue(isLogged)
        }.flowOn(Dispatchers.IO)
            .onStart {
                loadingMutableLiveData.postValue(true)
            }.launchIn(viewModelScope)
    }
}