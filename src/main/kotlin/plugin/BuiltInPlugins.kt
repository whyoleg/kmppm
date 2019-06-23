package dev.whyoleg.kamp.plugin

import dev.whyoleg.kamp.dependency.*

object BuiltInPlugins {
    val kotlinMpp = Plugin(PluginName.kotlinMpp, BuiltInDependencies.kotlin.plugin.gradle)
    val kotlinJvm = Plugin(PluginName.kotlinJvm, BuiltInDependencies.kotlin.plugin.gradle)
    val serialization = Plugin(PluginName.serialization, BuiltInDependencies.kotlin.plugin.serialization)
    val atomicfu = Plugin(PluginName.atomicfu, BuiltInDependencies.kotlinx.plugin.atomicfu)

    val shadow = Plugin(PluginName.shadowJar, BuiltInDependencies.shadow)
    val updates = Plugin(PluginName.updates, BuiltInDependencies.updates)

    val application = Plugin(PluginName.application, null)
    val docker = Plugin(PluginName.docker, BuiltInDependencies.docker)
    val detekt = Plugin(PluginName.detekt, BuiltInDependencies.detekt)

    internal val kamp = Plugin(PluginName.kamp, BuiltInDependencies.kamp)
}