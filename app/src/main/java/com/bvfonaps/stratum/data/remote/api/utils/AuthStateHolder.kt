package com.bvfonaps.stratum.data.remote.api.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


sealed interface ShowAuthState {
    object Closed: ShowAuthState
    object Open: ShowAuthState
}


class AuthStateHolder() {
    private val _showAuthState = MutableStateFlow<ShowAuthState>(
        ShowAuthState.Closed
    )
    val showAuthState: StateFlow<ShowAuthState> = _showAuthState.asStateFlow()

    fun closeAuthDialog() {
        _showAuthState.value = ShowAuthState.Closed
    }

    fun openAuthDialog() {
        _showAuthState.value = ShowAuthState.Open
    }
}