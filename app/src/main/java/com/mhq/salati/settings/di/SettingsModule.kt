package com.mhq.salati.settings.di

import com.mhq.salati.settings.data.repoimpl.SettingsRepoImpl
import com.mhq.salati.settings.domain.repo.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModule {

    @Binds
    abstract fun bindSettingsRepository(
        impl: SettingsRepoImpl
    ): SettingsRepository
}