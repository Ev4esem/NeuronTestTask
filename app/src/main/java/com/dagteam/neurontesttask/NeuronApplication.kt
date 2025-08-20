package com.dagteam.neurontesttask

import android.app.Application
import com.dagteam.neurontesttask.data.AccountRepositoryImpl
import com.dagteam.neurontesttask.data.datasources.LocalDataSource
import com.dagteam.neurontesttask.data.datasources.UserDataStorage
import com.dagteam.neurontesttask.domain.repositories.AccountRepository

class NeuronApplication : Application() {
    
    val accountRepository: AccountRepository by lazy {
        AccountRepositoryImpl(
            LocalDataSource(this),
            UserDataStorage(this)
        )
    }
}