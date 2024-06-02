package com.jlmillan.sudokumaster.data.dto

import com.jlmillan.sudokumaster.domain.model.UserModel

data class UserDTO(
    val name: String? = "",
    val username: String? = "",
    val email: String? = "",
    val id: String? = "",
    val hashId: String? = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "name" to name,
            "username" to username,
            "email" to email,
            "id" to id
        )
    }
}

fun UserDTO.toModel() : UserModel {
    return UserModel(
        name = this.name.orEmpty(),
        username = this.username.orEmpty(),
        email = this.email.orEmpty(),
        id = this.id.orEmpty(),
        hashId = this.hashId.orEmpty()
    )
}