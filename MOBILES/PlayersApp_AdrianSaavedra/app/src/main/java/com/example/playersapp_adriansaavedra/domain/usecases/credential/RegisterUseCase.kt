package com.example.playersapp_adriansaavedra.domain.usecases.credential

import com.example.playersapp_adriansaavedra.data.LoginRepository
import com.example.playersapp_adriansaavedra.data.remote.model.Register
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(username: String, password: String, email: String)= loginRepository.register(Register(username, password, email))
}