package com.project.nit3213.di

import com.project.nit3213.data.repository.AuthRepository
import com.project.nit3213.data.repository.AuthRepositoryImpl
import com.project.nit3213.data.repository.DashboardRepository
import com.project.nit3213.data.repository.DashboardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Hilt module binding repository interfaces to their concrete implementations
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // Binds AuthRepositoryImpl implementation to AuthRepository interface
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    // Binds DashboardRepositoryImpl implementation to DashboardRepository interface
    @Binds
    @Singleton
    abstract fun bindDashboardRepository(impl: DashboardRepositoryImpl): DashboardRepository
}

