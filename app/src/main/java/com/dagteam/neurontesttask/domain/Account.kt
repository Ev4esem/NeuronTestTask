package com.dagteam.neurontesttask.domain

data class Account(
    val id: Long,
    val phone: String,
    val email: String,
    val user: User,
)
