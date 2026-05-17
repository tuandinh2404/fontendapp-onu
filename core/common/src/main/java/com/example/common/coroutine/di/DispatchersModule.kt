package com.example.common.coroutine.di

import com.example.common.coroutine.Dispatcher
import com.example.common.coroutine.OnuDispatchers.IO
import com.example.common.coroutine.OnuDispatchers.Default
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {
    @Provides
    @Dispatcher(IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    @Dispatcher(Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}