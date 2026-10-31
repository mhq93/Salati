package com.mhq.salati.shared.di

import com.mhq.salati.shared.data.repoimpl.PrayerNameResolverImpl
import com.mhq.salati.shared.domain.PrayerNameResolver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PrayerNameModule {

    @Binds
    @Singleton
    abstract fun bindPrayerNameResolver(impl: PrayerNameResolverImpl): PrayerNameResolver
}