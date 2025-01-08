package com.example.usersapp_adriansaavedra.data.remote.datasource

import com.example.usersapp_adriansaavedra.data.remote.modelo.toUser
import com.example.usersapp_adriansaavedra.data.remote.services.UserService
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(
    private val userService: UserService,
) : BaseApiResponse() {
    suspend fun fetchUsers() = safeApiCall { userService.getUsers() }.map { list -> list.map { it.toUser() } }
}