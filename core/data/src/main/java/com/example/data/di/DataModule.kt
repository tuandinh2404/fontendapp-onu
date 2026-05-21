package com.example.data.di

import com.example.data.auth.DatastoreTokenProvider
import com.example.domain.repository.AuthRepository
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.WeatherRepositoryImpl
import com.example.domain.repository.WeatherRepository
import com.example.network.auth.TokenProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindTokenProvider(
        impl: DatastoreTokenProvider
    ): TokenProvider

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}