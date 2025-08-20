package com.dagteam.neurontesttask.data

import com.dagteam.neurontesttask.data.datasources.LocalDataSource
import com.dagteam.neurontesttask.data.datasources.UserDataStorage
import com.dagteam.neurontesttask.domain.entities.Purchase
import com.dagteam.neurontesttask.domain.entities.UserData
import com.dagteam.neurontesttask.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AccountRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val userDataStorage: UserDataStorage,
): AccountRepository {

    private val userDataFlow = MutableStateFlow(userDataStorage.getUserData())

    override fun getUserData(): Flow<UserData> {
        return userDataFlow
    }

    override suspend fun getMyBuys(): List<Purchase> {
        return localDataSource.getLocalData()
    }

    override suspend fun setUserData(userData: UserData) {
        userDataStorage.saveUserData(userData)
        userDataFlow.value = userData
    }
}