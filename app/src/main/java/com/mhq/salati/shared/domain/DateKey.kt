package com.mhq.salati.shared.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// Pinned to Locale.US so digits never depend on the app language.
private val DateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

/** The "dd-MM-yyyy" key that prayer times and muted prayers are stored under. */
fun LocalDate.toDateKey(): String = format(DateKeyFormatter)

fun String.toLocalDateFromKey(): LocalDate = LocalDate.parse(this, DateKeyFormatter)