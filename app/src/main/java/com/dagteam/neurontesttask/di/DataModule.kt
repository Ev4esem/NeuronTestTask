package com.dagteam.neurontesttask.di

import android.content.Context
import com.dagteam.neurontesttask.data.AccountRepositoryImpl
import com.dagteam.neurontesttask.data.datasources.LocalDataSource
import com.dagteam.neurontesttask.data.datasources.UserDataStorage
import com.dagteam.neurontesttask.domain.repositories.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(@ApplicationContext context: Context): LocalDataSource {
        return LocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideUserDataStorage(@ApplicationContext context: Context): UserDataStorage {
        return UserDataStorage(context)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(
        localDataSource: LocalDataSource,
        userDataStorage: UserDataStorage
    ): AccountRepository {
        return AccountRepositoryImpl(localDataSource, userDataStorage)
    }
}