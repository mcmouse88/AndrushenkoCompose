package com.mcmouse88.appnavigation.di

import com.mcmouse88.appnavigation.ItemsRepository
import com.mcmouse88.appnavigation.ItemsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ItemsRepositoryModule {

    @Binds
    fun bindItemsRepository(impl: ItemsRepositoryImpl): ItemsRepository
}