package com.example.usersapp_adriansaavedra.domain.usecases.usuarios

import com.example.usersapp_adriansaavedra.data.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val usersRepository: UserRepository) {
    suspend operator fun invoke() = usersRepository.fetchUsersHttp()
    suspend fun getUsersFromCache() = usersRepository.fetchUsersCache()
}