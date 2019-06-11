package dev.whyoleg.kamp.plugin

import dev.whyoleg.kamp.dependency.*

object BuiltInPlugins {
    val kotlinMpp = Plugin(PluginName.kotlinMpp, BuiltInDependencies.Kotlin.Plugin.gradle)
    val kotlinJvm = Plugin(PluginName.kotlinJvm, BuiltInDependencies.Kotlin.Plugin.gradle)
    val serialization = Plugin(PluginName.serialization, BuiltInDependencies.Kotlin.Plugin.serialization) {
        maven { it.setUrl("https://dl.bintray.com/kotlin/kotlin-eap") } //TODO remove after 1.3.40 release
    }
    val atomicfu = Plugin(PluginName.atomicfu, BuiltInDependencies.KotlinX.Plugin.atomicfu) {
        maven { it.setUrl("https://kotlin.bintray.com/kotlinx") }
    }

    val shadow = Plugin(PluginName.shadowJar, BuiltInDependencies.shadow) { maven { it.setUrl("https://plugins.gradle.org/m2/") } }
    val updates = Plugin(PluginName.updates, BuiltInDependencies.updates) { maven { it.setUrl("https://plugins.gradle.org/m2/") } }

    val application = Plugin(PluginName.application, null)
    val docker = Plugin(PluginName.docker, BuiltInDependencies.docker) { google() }
    val detekt = Plugin(PluginName.detekt, BuiltInDependencies.detekt) { maven { it.setUrl("https://plugins.gradle.org/m2/") } }
}