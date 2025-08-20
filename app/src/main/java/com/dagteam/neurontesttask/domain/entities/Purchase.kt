package com.dagteam.neurontesttask.domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Purchase(
    val date: String,
    val name: List<String>,
)
