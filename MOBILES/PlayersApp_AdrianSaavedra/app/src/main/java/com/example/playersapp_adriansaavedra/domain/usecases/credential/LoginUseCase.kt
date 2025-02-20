package com.example.playersapp_adriansaavedra.domain.usecases.credential

import com.example.playersapp_adriansaavedra.data.LoginRepository
import com.example.playersapp_adriansaavedra.data.remote.model.Login
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    suspend operator fun invoke(username: String, password: String) =
        loginRepository.login(Login(username, password))


}