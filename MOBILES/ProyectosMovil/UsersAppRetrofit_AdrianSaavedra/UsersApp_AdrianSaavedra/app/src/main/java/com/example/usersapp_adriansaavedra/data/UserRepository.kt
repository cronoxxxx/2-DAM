package com.example.usersapp_adriansaavedra.data

import com.example.usersapp_adriansaavedra.data.remote.datasource.UsersRemoteDataSource
import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.domain.modelo.toUserRemote
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val usersRemoteDataSource: UsersRemoteDataSource,
) {
    suspend fun fetchUsers() = usersRemoteDataSource.fetchUsers()
    suspend fun fetchUser(id: Int) = usersRemoteDataSource.fetchUser(id)
    suspend fun addUser(user: User) = usersRemoteDataSource.addUser(user.toUserRemote())
    suspend fun updateUser(id: Int, user: User) = usersRemoteDataSource.updateUser(id, user.toUserRemote())
    suspend fun deleteUser(id: Int) = usersRemoteDataSource.deleteUser(id)
}