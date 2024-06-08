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

    private val registerSuccessLiveData = MutableLiveData<Pair<Boolean, AuthErrorException?>>()
    private val registerLoadingLiveData = MutableLiveData<Boolean>()

    fun registerSuccessLiveData(): LiveData<Pair<Boolean, AuthErrorException?>> = registerSuccessLiveData
    fun registerLoadingLiveData(): LiveData<Boolean> = registerLoadingLiveData

    fun register(password: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            registerLoadingLiveData.postValue(true)
            val result = AuthRepositoryImpl.register(password, email)
            registerSuccessLiveData.postValue(result)
            registerLoadingLiveData.postValue(false)
        }
    }

    private fun getErrorMessage(authError: AuthErrorException?): String {
        return when (authError) {
//            AuthErrorException.USER_ALREADY_EXIST -> "El usuario ya existe, debe utilizar otro nombre de usuario"
            AuthErrorException.EMAIL_EXIST -> "El email ya existe, debe utilizar otro nombre de usuario"
            AuthErrorException.UNKNOWN -> "Ha ocurrido un error al realizar el registro"
            else -> "Error desconocido"
        }
    }
}
