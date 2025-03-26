package com.example.finalexamenmoviles_adriansaavedra.data.remote.datasource

import com.example.finalexamenmoviles_adriansaavedra.data.remote.services.LoginService
import com.example.finalexamenmoviles_adriansaavedra.data.remote.utils.LoginUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRemoteDataSource @Inject constructor(private val loginService: LoginService): BaseApiResponse(){
suspend fun login(username: String, password: String) = safeApiCall { loginService.login(LoginUser(username, password)) }
}