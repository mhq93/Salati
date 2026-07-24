package com.mhq.salati.di.settings

import com.mhq.salati.data.repoimpl.settings.SettingsRepoImpl
import com.mhq.salati.domain.repo.settings.SettingsRepository
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