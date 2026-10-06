package com.mhq.salati.alarms.di

import com.mhq.salati.alarms.data.repoimpl.CustomAlarmRepoImpl
import com.mhq.salati.alarms.data.repoimpl.CustomAlarmSchedulerImpl
import com.mhq.salati.alarms.datasource.alarm.AndroidCustomAlarmDataSource
import com.mhq.salati.alarms.datasource.alarm.CustomAlarmDataSource
import com.mhq.salati.alarms.datasource.database.CustomAlarmDao
import com.mhq.salati.alarms.domain.repo.CustomAlarmRepository
import com.mhq.salati.alarms.domain.repo.CustomAlarmScheduler
import com.mhq.salati.alarms.ui.receiver.CustomAlarmReceiver
import com.mhq.salati.shared.datasource.database.SalatiDatabase
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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

@Module
@InstallIn(SingletonComponent::class)
object AlarmsProvidesModule {
    @Provides
    fun provideCustomAlarmDao(database: SalatiDatabase): CustomAlarmDao = database.customAlarmDao()

    // The DI module is the one place that knows both the DataSource and the receiver class.
    @Provides
    @Named("custom_alarm_receiver")
    fun provideCustomAlarmReceiver(): ComponentTarget =
        ComponentTarget(CustomAlarmReceiver::class.java)
}