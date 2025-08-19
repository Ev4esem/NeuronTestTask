package com.dagteam.neurontesttask.domain.repositories

import com.dagteam.neurontesttask.domain.entities.Account
import com.dagteam.neurontesttask.domain.entities.Purchase
import com.dagteam.neurontesttask.domain.entities.UserData
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun getAccount(): Flow<Account>

    suspend fun getMyBuys(): List<Purchase>

    suspend fun setUserData(userData: UserData)

}