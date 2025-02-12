package com.example.usersapp_adriansaavedra.domain.usecases.usuarios
import com.example.usersapp_adriansaavedra.data.UserRepository
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.domain.modelo.validate
import javax.inject.Inject

class AddUserUseCase @Inject constructor(private val usersRepository: UserRepository) {

    suspend operator fun invoke(user: User) =
        validarLlamada()
            .then { usersRepository.addUser(user) }
            .then { it.validate() }

    private fun validarLlamada() =
        NetworkResult.Success(true)

}