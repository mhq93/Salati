package com.mhq.salati.permissions.di

import com.mhq.salati.permissions.domain.repo.PermissionChecker
import com.mhq.salati.permissions.data.repoimpl.PermissionCheckerImpl
import com.mhq.salati.permissions.datasource.device.AndroidPermissionDataSource
import com.mhq.salati.permissions.datasource.device.PermissionDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PermissionModule {

    @Binds
    @Singleton
    abstract fun bindPermissionChecker(impl: PermissionCheckerImpl): PermissionChecker

    @Binds
    @Singleton
    abstract fun bindPermissionDataSource(impl: AndroidPermissionDataSource): PermissionDataSource
}