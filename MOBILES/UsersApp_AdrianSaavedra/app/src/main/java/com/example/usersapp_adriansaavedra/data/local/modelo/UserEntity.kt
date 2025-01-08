package com.example.usersapp_adriansaavedra.data.local.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.usersapp_adriansaavedra.domain.modelo.User

//poner delay para comprobar loading
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
    val password : String,
    val email: String,
    val street: String,
    val city: String,
    val phone: String
)

fun UserEntity.toUser() = User(
    id = this.id,
    name = this.name,
    username = this.username,
    password = this.password,
    email = this.email,
    street = this.street,
    city = this.city,
    phone = this.phone
)


