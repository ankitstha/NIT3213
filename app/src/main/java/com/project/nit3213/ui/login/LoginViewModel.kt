package com.project.nit3213.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.nit3213.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Username and password cannot be blank")
            return
        }
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                if (response.isSuccessful) {
                    val body = response.body()
                    val keypass = body?.keypass
                    if (!keypass.isNullOrBlank()) {
                        _loginState.value = LoginState.Success(keypass)
                    } else {
                        _loginState.value = LoginState.Error("Keypass not found in response")
                    }
                } else {
                    val errorMsg = response.errorBody()?.string() ?: response.message()
                    _loginState.value = LoginState.Error("Login failed: $errorMsg")
                }
            } catch (e: SocketTimeoutException) {
                _loginState.value = LoginState.Error("Connection timeout. The server is waking up (cold start). Please try again.")
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }
}

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val keypass: String) : LoginState()
    data class Error(val message: String) : LoginState()
}
