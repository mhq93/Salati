package com.mhq.salati.adhan.di

import com.mhq.salati.adhan.data.repoimpl.MutedPrayersRepoImpl
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MutedPrayersModule {
    @Binds
    abstract fun bindMutedPrayersRepository(impl: MutedPrayersRepoImpl): MutedPrayersRepository
}