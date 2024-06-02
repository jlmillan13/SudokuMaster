package com.jlmillan.sudokumaster.ui.feature.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jlmillan.sudokumaster.data.auth.AuthRepositoryImpl
import com.jlmillan.sudokumaster.domain.model.AuthErrorException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val registerSuccessLiveData by lazy {
        MutableLiveData<Pair<Boolean, AuthErrorException?>>()
    }
    private val registerLoadingLiveData by lazy {
        MutableLiveData<Boolean>()
    }

    fun registerSuccessLiveData() : LiveData<Pair<Boolean, AuthErrorException?>> = registerSuccessLiveData
    fun registerLoadingLiveData() : LiveData<Boolean> = registerLoadingLiveData

    fun register(username: String, password: String, email: String, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            registerLoadingLiveData.postValue(true) // Pantalla en modo carga
            val result = AuthRepositoryImpl.register(username, password, email, name)
            registerSuccessLiveData.postValue(result) // Retorno el valor del registro
            registerLoadingLiveData.postValue(false) // Quito pantalla en modo carga
        }
    }
}