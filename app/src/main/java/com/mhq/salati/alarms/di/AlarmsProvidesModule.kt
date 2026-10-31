package com.mhq.salati.alarms.di

import com.mhq.salati.alarms.datasource.database.CustomAlarmDao
import com.mhq.salati.alarms.ui.receiver.CustomAlarmReceiver
import com.mhq.salati.shared.datasource.database.SalatiDatabase
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

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