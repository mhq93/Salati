package com.mhq.salati.qibla.di

import com.mhq.salati.qibla.data.repoimpl.CompassRepoImpl
import com.mhq.salati.qibla.datasource.device.AndroidRotationSensorDataSource
import com.mhq.salati.qibla.datasource.device.RotationSensorDataSource
import com.mhq.salati.qibla.domain.repo.CompassRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class QiblaModule {

    @Binds
    @Singleton
    abstract fun bindCompassRepository(impl: CompassRepoImpl): CompassRepository

    @Binds
    @Singleton
    abstract fun bindRotationSensorDataSource(impl: AndroidRotationSensorDataSource): RotationSensorDataSource
}