package com.mhq.salati.home.di

import com.mhq.salati.home.domain.usecases.CalculateCurrentPrayerNameUseCase
import com.mhq.salati.home.domain.usecases.CalculateNextPrayerInfoUseCase
import com.mhq.salati.home.domain.usecases.ResolveNextPrayerInfoForDisplayUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetCachedPrayerTimesUseCase
import com.mhq.salati.prayertimes.domain.usecases.GetPrayerTimesUseCase
import com.mhq.salati.shared.domain.Clock
import com.mhq.salati.shared.domain.usecases.ParseTimeToMinutesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

//@Module
//@InstallIn(ViewModelComponent::class)
//object HomeUseCaseModule {
//
//    @Provides
//    @ViewModelScoped
//    fun provideCalculateCurrentPrayerNameUseCase(
//        parseTimeToMinutesUseCase: ParseTimeToMinutesUseCase,
//        clock: Clock
//    ): CalculateCurrentPrayerNameUseCase {
//        return CalculateCurrentPrayerNameUseCase(parseTimeToMinutesUseCase, clock)
//    }
//
//    @Provides
//    @ViewModelScoped
//    fun provideResolveNextPrayerInfoForDisplayUseCase(
//        getCachedPrayerTimesUseCase: GetCachedPrayerTimesUseCase,
//        getPrayerTimesUseCase: GetPrayerTimesUseCase,
//        calculateNextPrayerInfoUseCase: CalculateNextPrayerInfoUseCase,
//        clock: Clock
//    ): ResolveNextPrayerInfoForDisplayUseCase {
//        return ResolveNextPrayerInfoForDisplayUseCase(
//            getCachedPrayerTimesUseCase,
//            getPrayerTimesUseCase,
//            calculateNextPrayerInfoUseCase,
//            clock
//        )
//    }
//}