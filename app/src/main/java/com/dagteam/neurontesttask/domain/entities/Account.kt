package com.dagteam.neurontesttask.domain.entities

data class Account(
    val id: Long,
    val userData: UserData,
    val isEnterBiometry: Boolean,
    val isConfirmedEmail: Boolean,
    val phone: String,
    val email: String,
)
