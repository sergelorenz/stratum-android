package com.bvfonaps.stratum.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bvfonaps.stratum.data.remote.api.ApiResult
import com.bvfonaps.stratum.data.remote.api.utils.AuthStateHolder
import com.bvfonaps.stratum.data.repositories.interfaces.IAuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface AuthTypeState {
    object Login: AuthTypeState
    object Register: AuthTypeState
}


sealed interface AuthResultState {
    object Idle: AuthResultState
    object Authenticating: AuthResultState
    object Success: AuthResultState
    data class Error(val message: String): AuthResultState
}


class AuthViewModel(
    private val authStateHolder: AuthStateHolder,
    private val authRepository: IAuthRepository
): ViewModel() {
    val showAuthState = authStateHolder.showAuthState
    private val _authResultState = MutableStateFlow<AuthResultState>(
        AuthResultState.Idle
    )
    val authResultState: StateFlow<AuthResultState> = _authResultState.asStateFlow()

    private val _authTypeState = MutableStateFlow<AuthTypeState>(
        AuthTypeState.Login
    )
    val authTypeState: StateFlow<AuthTypeState> = _authTypeState.asStateFlow()

    fun openAuthDialog() {
        authStateHolder.openAuthDialog()
    }

    fun closeAuthDialog() {
        authStateHolder.closeAuthDialog()
    }

    fun switchToRegisterAuthDialog() {
        viewModelScope.launch {
            _authResultState.value = AuthResultState.Idle
            closeAuthDialog()
            delay(500)
            _authTypeState.value = AuthTypeState.Register
            openAuthDialog()
        }
    }

    fun switchToLoginAuthDialog() {
        viewModelScope.launch {
            _authResultState.value = AuthResultState.Idle
            closeAuthDialog()
            delay(500)
            _authTypeState.value = AuthTypeState.Login
            openAuthDialog()
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authResultState.value = AuthResultState.Authenticating
            when (val loginResult = authRepository.login(username, password)) {
                is ApiResult.Success -> {
                    val token = loginResult.data
                    _authResultState.value = AuthResultState.Success
                }
                is ApiResult.Error -> {
                    val errorMessage = loginResult.message
                    _authResultState.value = AuthResultState.Error(errorMessage)
                }
            }
        }
    }

    fun register(
        username: String,
        password: String,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            _authResultState.value = AuthResultState.Authenticating
            when (val registerResult = authRepository.register(username, password, confirmPassword)) {
                is ApiResult.Success -> {
                    val token = registerResult.data
                    _authResultState.value = AuthResultState.Success
                }
                is ApiResult.Error -> {
                    val errorMessage = registerResult.message
                    _authResultState.value = AuthResultState.Error(errorMessage)
                }
            }
        }
    }
}