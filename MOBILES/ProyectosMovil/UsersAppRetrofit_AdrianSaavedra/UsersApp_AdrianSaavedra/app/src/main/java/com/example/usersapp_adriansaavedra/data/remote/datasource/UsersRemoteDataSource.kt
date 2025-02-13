package com.example.usersapp_adriansaavedra.data.remote.datasource

import com.example.usersapp_adriansaavedra.data.remote.modelo.UserRemote
import com.example.usersapp_adriansaavedra.data.remote.modelo.toUser
import com.example.usersapp_adriansaavedra.data.remote.services.UserService
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(
    private val userService: UserService,
) : BaseApiResponse() {
    suspend fun fetchUsers() =
        safeApiCall { userService.getUsers() }.map { list ->
            list.map { it.toUser() }}

    suspend fun fetchUser(id: Int) =
        safeApiCall { userService.getUser(id) }.map { it.toUser() }

    suspend fun addUser(user: UserRemote) =
        safeApiCall { userService.addUser(user) }.map { it.toUser() }

    suspend fun updateUser(id: Int, user: UserRemote) =
        safeApiCall { userService.updateUser(id, user) }.map { it.toUser() }

    suspend fun deleteUser(id: Int) = safeApiCall { userService.deleteUser(id) }
}