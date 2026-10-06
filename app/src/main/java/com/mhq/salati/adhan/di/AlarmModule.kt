package com.mhq.salati.adhan.di

import com.mhq.salati.adhan.data.repoimpl.AlarmSchedulerImpl
import com.mhq.salati.adhan.datasource.alarm.AndroidPrayerAlarmDataSource
import com.mhq.salati.adhan.datasource.alarm.PrayerAlarmDataSource
import com.mhq.salati.adhan.domain.repo.AlarmScheduler
import com.mhq.salati.adhan.ui.receiver.PrayerAlarmReceiver
import com.mhq.salati.shared.datasource.device.ComponentTarget
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmModule {

    @Binds
    abstract fun bindAlarmScheduler(impl: AlarmSchedulerImpl): AlarmScheduler

    @Binds
    abstract fun bindPrayerAlarmDataSource(impl: AndroidPrayerAlarmDataSource): PrayerAlarmDataSource

    companion object {
        // The DI module is the one place that knows both the DataSource and the receiver class.
        @Provides
        @Named("prayer_alarm_receiver")
        fun providePrayerAlarmReceiver(): ComponentTarget = ComponentTarget(PrayerAlarmReceiver::class.java)
    }
}