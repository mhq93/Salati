package com.mhq.salati.alarms.di

import com.mhq.salati.alarms.data.local.CustomAlarmDao
import com.mhq.salati.alarms.data.repoimpl.CustomAlarmRepoImpl
import com.mhq.salati.alarms.data.scheduler.AndroidCustomAlarmScheduler
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import com.mhq.salati.alarms.domain.scheduler.CustomAlarmScheduler
import com.mhq.salati.shared.data.local.SalatiDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmsBindsModule {
    @Binds
    @Singleton
    abstract fun bindCustomAlarmRepository(impl: CustomAlarmRepoImpl): CustomAlarmRepository

    @Binds
    @Singleton
    abstract fun bindCustomAlarmScheduler(impl: AndroidCustomAlarmScheduler): CustomAlarmScheduler
}

@Module
@InstallIn(SingletonComponent::class)
object AlarmsProvidesModule {
    @Provides
    fun provideCustomAlarmDao(database: SalatiDatabase): CustomAlarmDao = database.customAlarmDao()
}