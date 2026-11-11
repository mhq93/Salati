package com.mhq.salati.alarms.presentation.mapper

import com.mhq.salati.alarms.domain.model.OffsetDirection
import com.mhq.salati.alarms.presentation.model.OffsetDirectionUi

fun OffsetDirection.toUi(): OffsetDirectionUi = when (this) {
    OffsetDirection.BEFORE -> OffsetDirectionUi.BEFORE
    OffsetDirection.AFTER -> OffsetDirectionUi.AFTER
}

fun OffsetDirectionUi.toDomain(): OffsetDirection = when (this) {
    OffsetDirectionUi.BEFORE -> OffsetDirection.BEFORE
    OffsetDirectionUi.AFTER -> OffsetDirection.AFTER
}

/**
 * Reads the direction key the custom alarm receiver gets in its Intent.
 * CustomAlarmSchedulerImpl writes it as `OffsetDirection.name`.
 * A missing or unknown key gives null.
 */
fun String?.toOffsetDirectionUiOrNull(): OffsetDirectionUi? =
    this?.let { key -> runCatching { OffsetDirection.valueOf(key) }.getOrNull() }?.toUi()