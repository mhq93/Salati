package com.mhq.salati.shared.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Formatter for the stable string key a calendar date is stored under.
 * Pinned to [Locale.US] so the digits never depend on the app or device language;
 * Arabic locale settings must not change what is written to storage.
 */
private val DateKeyFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US)

/**
 * The "dd-MM-yyyy" key that prayer times and muted prayers are stored under.
 * This key is part of the persisted data format:
 * changing the pattern would orphan every previously saved row.
 */
fun LocalDate.toDateKey(): String = format(DateKeyFormatter)

/**
 * Parses a key produced by [toDateKey] back into a [LocalDate].
 *
 * @throws java.time.format.DateTimeParseException if the string is not a valid "dd-MM-yyyy" key
 */
fun String.toLocalDateFromKey(): LocalDate = LocalDate.parse(this, DateKeyFormatter)