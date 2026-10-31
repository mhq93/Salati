package com.mhq.salati.shared.data.repoimpl

import com.mhq.salati.shared.data.mapper.fromStorageKey
import com.mhq.salati.shared.domain.PrayerName
import com.mhq.salati.shared.domain.PrayerNameResolver
import javax.inject.Inject

class PrayerNameResolverImpl @Inject constructor() : PrayerNameResolver {
    override fun resolve(key: String): PrayerName? = PrayerName.fromStorageKey(key)
}