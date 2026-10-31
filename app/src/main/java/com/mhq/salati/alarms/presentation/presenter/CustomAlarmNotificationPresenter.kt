package com.mhq.salati.alarms.presentation.presenter

import com.mhq.salati.alarms.presentation.mapper.toOffsetDirectionUiOrNull
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi
import com.mhq.salati.settings.domain.usecases.ObserveSettingsUseCase
import com.mhq.salati.settings.presentation.mapper.toUi
import com.mhq.salati.settings.presentation.model.CustomAlarmSoundUi
import com.mhq.salati.shared.domain.usecases.ResolvePrayerNameUseCase
import com.mhq.salati.shared.presentation.mapper.toUi
import com.mhq.salati.shared.presentation.model.PrayerNameUi
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/** What the custom alarm notification needs to know when an alarm rings. */
class CustomAlarmNotificationPresenter @Inject constructor(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val resolvePrayerNameUseCase: ResolvePrayerNameUseCase
) {
    /** The sound the user picked for custom alarms. */
    suspend fun customAlarmSound(): CustomAlarmSoundUi =
        observeSettingsUseCase().first().customAlarmSound.toUi()

    /** The prayer an Intent key stands for, or null when the key is unknown. */
    fun prayerNameFor(prayerKey: String): PrayerNameUi? =
        resolvePrayerNameUseCase(prayerKey)?.toUi()

    /** The direction an Intent key stands for, or null when it is missing or unknown. */
    fun offsetDirectionFor(directionKey: String?): OffsetDirectionUi? =
        directionKey.toOffsetDirectionUiOrNull()
}