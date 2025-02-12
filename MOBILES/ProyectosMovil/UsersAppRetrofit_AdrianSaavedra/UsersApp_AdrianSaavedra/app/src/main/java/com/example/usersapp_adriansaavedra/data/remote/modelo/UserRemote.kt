package com.example.usersapp_adriansaavedra.data.remote.modelo

import com.example.usersapp_adriansaavedra.domain.modelo.User

data class UserRemote(
    val address: Address,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
)
fun UserRemote.toUser() =
        User(
            street = address.street,
            city = address.city,
            email = email,
            id = id,
            name = name,
            phone = phone,
            username = username,
        )

