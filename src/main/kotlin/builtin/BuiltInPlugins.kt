package dev.whyoleg.kamp.builtin

import dev.whyoleg.kamp.plugin.*

open class BuiltInPlugins(dependencies: BuiltInDependencies) {
    val kotlinMpp = Plugin("kotlin-multiplatform", dependencies.kotlin.plugin.gradle)
    val kotlinJvm = Plugin("org.jetbrains.kotlin.jvm", dependencies.kotlin.plugin.gradle)
    val serialization = Plugin("kotlinx-serialization", dependencies.kotlin.plugin.serialization)
    val atomicfu = Plugin("kotlinx-atomicfu", dependencies.kotlinx.plugin.atomicfu)

    val shadow = Plugin("com.github.johnrengelman.shadow", dependencies.shadow)
    val updates = Plugin("com.github.ben-manes.versions", dependencies.updates)

    val application = Plugin("org.gradle.application", null)
    val docker = Plugin("com.google.cloud.tools.jib", dependencies.docker)
    val detekt = Plugin("io.gitlab.arturbosch.detekt", dependencies.detekt)

    internal val kamp = Plugin("dev.whyoleg.kamp", dependencies.kamp)
}