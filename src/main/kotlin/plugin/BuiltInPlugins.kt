package dev.whyoleg.kamp.plugin

import dev.whyoleg.kamp.dependency.*

object BuiltInPlugins {
    val kotlinMpp = Plugin(PluginName.kotlinMpp, BuiltInDependencies.Kotlin.Plugin.gradle)
    val kotlinJvm = Plugin(PluginName.kotlinJvm, BuiltInDependencies.Kotlin.Plugin.gradle)
    val serialization = Plugin(PluginName.serialization, BuiltInDependencies.Kotlin.Plugin.serialization)
    val atomicfu = Plugin(PluginName.atomicfu, BuiltInDependencies.Kotlinx.Plugin.atomicfu)

    val shadow = Plugin(PluginName.shadowJar, BuiltInDependencies.shadow)
    val updates = Plugin(PluginName.updates, BuiltInDependencies.updates)

    val application = Plugin(PluginName.application, null)
    val docker = Plugin(PluginName.docker, BuiltInDependencies.docker)

}