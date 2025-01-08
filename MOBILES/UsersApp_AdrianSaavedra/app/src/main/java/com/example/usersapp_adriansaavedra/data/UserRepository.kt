package com.example.usersapp_adriansaavedra.data

import com.example.usersapp_adriansaavedra.data.local.UserDao
import com.example.usersapp_adriansaavedra.data.local.modelo.toUser
import com.example.usersapp_adriansaavedra.data.remote.NetworkResult
import com.example.usersapp_adriansaavedra.data.remote.datasource.UsersRemoteDataSource
import com.example.usersapp_adriansaavedra.di.IoDispatcher
import com.example.usersapp_adriansaavedra.domain.modelo.User
import com.example.usersapp_adriansaavedra.domain.modelo.toUserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val usersRemoteDataSource: UsersRemoteDataSource,
    private val userDao: UserDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun fetchUsersHttp() = flow {
        emit(NetworkResult.Loading())
        val remoteResult = usersRemoteDataSource.fetchUsers()
        emit(remoteResult)
    }.catch { e -> emit(NetworkResult.Error(e.message ?: e.toString())) }
        .flowOn(dispatcher)

    suspend fun fetchUsersCache() = flow {
        emit(NetworkResult.Loading())
        val cachedUsers = userDao.getUsers().map { it.toUser() }
        emit(NetworkResult.Success(cachedUsers))
    }.catch { emit(NetworkResult.Error(it.message ?: it.toString())) }
        .flowOn(dispatcher)

    suspend fun fetchUser(id: Int) = flow {
        emit(NetworkResult.Loading())
        val cachedUser = userDao.getUser(id).toUser()
        emit(NetworkResult.Success(cachedUser))
    }.catch { emit(NetworkResult.Error(it.message ?: it.toString())) }
        .flowOn(dispatcher)

    suspend fun insertUser(user: User) = flow {
        emit(NetworkResult.Loading())
        val obtained = userDao.insertUser(user.toUserEntity())
        emit(NetworkResult.Success(obtained))
    }.catch { emit(NetworkResult.Error(it.message ?: it.toString())) }
        .flowOn(dispatcher)

    suspend fun updateUser(user: User) = flow {
        emit(NetworkResult.Loading())
        val obtained = userDao.updateUser(user.toUserEntity())
        emit(NetworkResult.Success(obtained))
    }.catch { emit(NetworkResult.Error(it.message ?: it.toString())) }
        .flowOn(dispatcher)


    suspend fun deleteUser(id: Int) = flow {
        emit(NetworkResult.Loading())
        val obtained = userDao.deleteUser(id)
        emit(NetworkResult.Success(obtained))
    }.catch { emit(NetworkResult.Error(it.message ?: it.toString())) }
        .flowOn(dispatcher)
}
