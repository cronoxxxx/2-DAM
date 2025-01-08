package com.example.usersapp_adriansaavedra.ui.pantallaLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usersapp_adriansaavedra.R
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.domain.usecases.usuarios.AddUserUseCase
import com.example.usersapp_adriansaavedra.domain.usecases.usuarios.GetUsersUseCase
import com.example.usersapp_adriansaavedra.ui.common.StringProvider
import com.example.usersapp_adriansaavedra.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val stringProvider: StringProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.RegisterUser -> registerUser(event.user)
            is LoginEvent.LoginUser -> loginUser(event.user)
            is LoginEvent.AvisoVisto -> avisoVisto()
        }
    }


    private fun avisoVisto() {
        _uiState.update { currentState -> currentState.copy(aviso = null) }
    }

    private fun updateUser(existingUser: User, userArg: User) = existingUser.copy(password = userArg.password)


    private fun registerUser(userArg: User) {
        viewModelScope.launch {
            getUsersUseCase.invoke().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val existingUser  = result.data.find { it.username == userArg.username }
                        if (existingUser  != null) {
                            val updatedUser = updateUser(existingUser, userArg)
                            addUserUseCase.invoke(updatedUser).collect { obtained ->
                                when (obtained) {
                                    is NetworkResult.Success -> {
                                        _uiState.update { currentState ->
                                            currentState.copy(
                                                aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.exitosamente_registrado))
                                            )
                                        }
                                    }

                                    is NetworkResult.Loading -> {
                                        _uiState.update { currentState -> currentState.copy(isLoading = true) }
                                    }

                                    is NetworkResult.Error -> {
                                        _uiState.update { currentState ->
                                            currentState.copy(
                                                aviso = UiEvent.ShowSnackbar(
                                                    obtained.message
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    is NetworkResult.Loading -> {
                        _uiState.update { currentState -> currentState.copy(isLoading = true) }
                    }

                    is NetworkResult.Error -> {

                        _uiState.update { currentState ->
                            currentState.copy(
                                aviso = UiEvent.ShowSnackbar(
                                    result.message
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loginUser(user: User) {
        viewModelScope.launch {
            val cachedUsers = getUsersUseCase.getUsersFromCache()
            cachedUsers.collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val obtained = result.data.find { it.username == user.username }
                        if (obtained != null) {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    idLogin = obtained.id,
                                    aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.login_exitoso))
                                )
                            }
                        } else{
                            _uiState.update { currentState ->
                                currentState.copy(
                                    aviso = UiEvent.ShowSnackbar(stringProvider.getString(R.string.error_login))
                                )
                            }
                        }
                    }

                    is NetworkResult.Loading -> {
                        _uiState.update { currentState -> currentState.copy(isLoading = true) }
                    }

                    is NetworkResult.Error -> {
                        _uiState.update { currentState ->
                            currentState.copy(
                                aviso = UiEvent.ShowSnackbar(
                                    result.message
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

