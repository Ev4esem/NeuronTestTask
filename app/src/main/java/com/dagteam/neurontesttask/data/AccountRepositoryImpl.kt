package com.dagteam.neurontesttask.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.dagteam.neurontesttask.data.datasources.LocalDataSource
import com.dagteam.neurontesttask.data.datasources.UserDataStorage
import com.dagteam.neurontesttask.domain.entities.Account
import com.dagteam.neurontesttask.domain.entities.Purchase
import com.dagteam.neurontesttask.domain.entities.UserData
import com.dagteam.neurontesttask.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val userDataStorage: UserDataStorage,
): AccountRepository {

    private val _accountStateFlow = MutableStateFlow(
        createInitialAccount()
    )

    init {
        val savedUserData = userDataStorage.getUserData()
        _accountStateFlow.value = _accountStateFlow.value.copy(userData = savedUserData)
    }

    private fun createInitialAccount(): Account {
        return Account(
            id = 1L,
            userData = UserData.Empty,
            isEnterBiometry = true,
            isConfirmedEmail = false,
            phone = "+7 999 123-45-67",
            email = "user@example.com"
        )
    }

    override fun getAccount(): Flow<Account> {
        return _accountStateFlow.asStateFlow()
    }

    override suspend fun getMyBuys(): List<Purchase> {
        return localDataSource.getLocalData()
    }

    override suspend fun setUserData(userData: UserData) {
        userDataStorage.saveUserData(userData)
        _accountStateFlow.value = _accountStateFlow.value.copy(userData = userData)
    }
}