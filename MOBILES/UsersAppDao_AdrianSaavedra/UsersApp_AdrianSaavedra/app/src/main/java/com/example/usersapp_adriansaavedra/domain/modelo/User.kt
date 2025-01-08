package com.example.usersapp_adriansaavedra.domain.modelo

import com.example.usersapp_adriansaavedra.data.local.modelo.UserEntity

data class User(
    val street: String = "",
    val city: String = "",
    val email: String = "",
    val id: Int = 0,
    val name: String = "",
    val phone: String = "",
    val username: String = "",
    val password: String = ""
)


fun User.toUserEntity() = UserEntity(
    id = this.id,
    name = this.name,
    username = this.username,
    password = this.password,
    email = this.email,
    street = this.street,
    city = this.city,
    phone = this.phone
)



