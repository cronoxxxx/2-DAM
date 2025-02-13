package com.example.usersapp_adriansaavedra.domain.usecases.usuarios

import com.example.usersapp_adriansaavedra.data.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val usersRepository: UserRepository) {
    suspend operator fun invoke(id: Int) = usersRepository.fetchUser(id)
}