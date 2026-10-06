package com.mhq.salati.shared.datasource.device

/**
 * The Android component (a receiver or a service)
 * that an intent should be delivered to.
 * It is provided by the DI modules,
 * so a DataSource never has to import a UI class.
 */
class ComponentTarget(val componentClass: Class<*>)