package com.dagteam.neurontesttask.data.entities

import com.dagteam.neurontesttask.domain.entities.Purchase
import kotlinx.serialization.Serializable

@Serializable
data class PurchasesDTO(
    val data: List<Purchase>
)