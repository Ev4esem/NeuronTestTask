package com.dagteam.neurontesttask.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val name: String,
    val surname: String,
    val password: String,
    val creditNumber: String,
) {
    companion object {
        val Empty = UserData(
            name = "",
            surname = "",
            password = "",
            creditNumber = ""
        )
    }
}
