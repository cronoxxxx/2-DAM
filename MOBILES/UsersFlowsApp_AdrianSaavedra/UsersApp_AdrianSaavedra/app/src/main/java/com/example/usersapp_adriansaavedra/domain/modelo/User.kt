package com.example.usersapp_adriansaavedra.domain.modelo

import com.example.usersapp_adriansaavedra.data.remote.modelo.Address
import com.example.usersapp_adriansaavedra.data.remote.modelo.UserRemote

data class User(
    val street: String = "",
    val city: String = "",
    val email: String = "",
    val id: Int = 0,
    val name: String = "",
    val phone: String = "",
    val username: String = "",
)
fun User.toUserRemote() =
    UserRemote(
        address = Address(street = this.street, city = this.city),
        email = this.email,
        id = this.id,
        name = this.name,
        phone = this.phone,
        username = this.username,
    )

fun User.validate() = this.name.isNotBlank() && this.email.isNotBlank() && this.username.isNotBlank()

