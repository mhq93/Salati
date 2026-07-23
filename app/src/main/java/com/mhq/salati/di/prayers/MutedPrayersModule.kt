package com.mhq.salati.di.prayers

import com.mhq.salati.data.repoimpl.alarms.MutedPrayersRepoImpl
import com.mhq.salati.domain.repo.alarms.MutedPrayersRepository
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