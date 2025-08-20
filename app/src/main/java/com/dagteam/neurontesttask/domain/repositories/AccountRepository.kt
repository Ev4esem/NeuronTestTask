package com.dagteam.neurontesttask.domain.repositories

import com.dagteam.neurontesttask.domain.entities.Purchase
import com.dagteam.neurontesttask.domain.entities.UserData
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun getUserData(): Flow<UserData>

    suspend fun getMyBuys(): List<Purchase>

    suspend fun setUserData(userData: UserData)

}