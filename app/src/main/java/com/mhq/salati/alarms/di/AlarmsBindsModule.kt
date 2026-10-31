package com.mhq.salati.alarms.di

import com.mhq.salati.alarms.data.repoimpl.CustomAlarmRepoImpl
import com.mhq.salati.alarms.data.repoimpl.CustomAlarmSchedulerImpl
import com.mhq.salati.alarms.datasource.alarm.AndroidCustomAlarmDataSource
import com.mhq.salati.alarms.datasource.alarm.CustomAlarmDataSource
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import com.mhq.salati.alarms.domain.repo.CustomAlarmScheduler
import dagger.Binds
import dagger.Module
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
    abstract fun bindCustomAlarmScheduler(impl: CustomAlarmSchedulerImpl): CustomAlarmScheduler

    @Binds
    @Singleton
    abstract fun bindCustomAlarmDataSource(impl: AndroidCustomAlarmDataSource): CustomAlarmDataSource
}