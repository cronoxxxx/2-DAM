package com.example.usersapp_adriansaavedra.domain.usecases.usuarios

import com.example.usersapp_adriansaavedra.data.UserRepository
import com.example.usersapp_adriansaavedra.domain.modelo.User
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val usersRepository: UserRepository) {
    suspend operator fun invoke(user: User) = usersRepository.updateUser(user)

}