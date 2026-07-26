package com.mhq.salati.adhan.di

import com.mhq.salati.adhan.data.local.MutedPrayerDao
import com.mhq.salati.adhan.data.repoimpl.MutedPrayersRepoImpl
import com.mhq.salati.adhan.domain.repo.MutedPrayersRepository
import com.mhq.salati.shared.data.local.SalatiDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MutedPrayersModule {
    @Binds
    abstract fun bindMutedPrayersRepository(impl: MutedPrayersRepoImpl): MutedPrayersRepository

    companion object {
        @Provides
        @Singleton
        fun provideMutedPrayerDao(database: SalatiDatabase): MutedPrayerDao {
            return database.mutedPrayerDao()
        }
    }
}