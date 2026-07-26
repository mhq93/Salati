package com.mhq.salati.adhan.di

import com.mhq.salati.adhan.data.AndroidAlarmScheduler
import com.mhq.salati.adhan.domain.repo.AlarmScheduler
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