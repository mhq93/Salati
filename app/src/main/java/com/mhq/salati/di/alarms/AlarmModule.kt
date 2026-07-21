package com.mhq.salati.di.alarms

import com.mhq.salati.data.alarms.AndroidAlarmScheduler
import com.mhq.salati.domain.repo.alarms.AlarmScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmModule {
    @Binds
    abstract fun bindAlarmScheduler(impl: AndroidAlarmScheduler): AlarmScheduler
}