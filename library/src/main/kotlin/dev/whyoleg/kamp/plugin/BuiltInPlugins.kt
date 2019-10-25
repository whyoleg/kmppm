package dev.whyoleg.kamp.plugin

import dev.whyoleg.kamp.dependency.*

open class BuiltInPlugins {
    companion object : BuiltInPlugins()

    val kotlinMpp = Plugin("kotlin-multiplatform", BuiltInDependencies.kotlin.plugin.gradle)
    val kotlinJvm = Plugin("org.jetbrains.kotlin.jvm", BuiltInDependencies.kotlin.plugin.gradle)
    val serialization = Plugin("kotlinx-serialization", BuiltInDependencies.kotlin.plugin.serialization)
    val atomicfu = Plugin("kotlinx-atomicfu", BuiltInDependencies.kotlinx.plugin.atomicfu)

    val shadow = Plugin("com.github.johnrengelman.shadow", BuiltInDependencies.shadow)
    val updates = Plugin("com.github.ben-manes.versions", BuiltInDependencies.updates)

    val application = Plugin("org.gradle.application", null)
    val docker = Plugin("com.google.cloud.tools.jib", BuiltInDependencies.docker)
    val detekt = Plugin("io.gitlab.arturbosch.detekt", BuiltInDependencies.detekt)
    val mavenPublish = Plugin("org.gradle.maven-publish", null)
    val bintray = Plugin("com.jfrog.bintray", BuiltInDependencies.bintray)
    val javaPlugin = Plugin("org.gradle.java-gradle-plugin", null)
    val buildScan = Plugin("com.gradle.build-scan", BuiltInDependencies.buildScan)

    val androidLib = Plugin("com.android.library", BuiltInDependencies.androidPlugin)

    internal val kamp = Plugin("dev.whyoleg.kamp", BuiltInDependencies.kamp)
}
