package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.version.*

open class BuiltInDependencies(val builtInVersions: BuiltInVersions = BuiltInVersions.Stable) : DependencyBuilder() {
    companion object : BuiltInDependencies()

    val oldTargetsStyle = listOf(common("common"), jvm(), js("js"))

    val kotlin by lazy(::Kotlin)

    inner class Kotlin : GroupWithVersion by group("org.jetbrains.kotlin", "mavenCentral").version(builtInVersions.kotlin) {
        val plugin by lazy(::Plugin)

        inner class Plugin : GroupWithVersionTargets by jvm {
            val gradle = artifact("kotlin-gradle-plugin")
            val serialization = artifact("kotlin-serialization")
        }

        val stdlib = artifact("kotlin-stdlib").targets(oldTargetsStyle)
        val stdlib8 = stdlib.targets(common("common"), jvm("jdk8"), js("js"))
        val test = stdlib.artifact("kotlin-test")
        val annotations = artifact("kotlin-test").targets(common("annotations-common"), jvm("junit"))
        val reflect = artifact("kotlin-reflect").jvm
    }

    val kotlinx by lazy(::Kotlinx)

    inner class Kotlinx : GroupWithTargets by group("org.jetbrains.kotlinx", "mavenCentral").targets(oldTargetsStyle) {
        val plugin by lazy(::Plugin)

        inner class Plugin : GroupWithTargets by jvm {
            val atomicfu = artifact("atomicfu-gradle-plugin").version(builtInVersions.atomicfu)
        }

        val serialization = artifact("kotlinx-serialization-runtime").version(builtInVersions.serialization)
        val configParser = serialization.artifact("kotlinx-serialization-runtime-configparser").jvm
        val atomicfu = artifact("atomicfu").version(builtInVersions.atomicfu)
    }

    val coroutines by lazy(::Coroutines)

    inner class Coroutines : GroupWithVersion by group("org.jetbrains.kotlinx", "mavenCentral").version(builtInVersions.coroutines) {
        val javaFX = artifact("kotlinx-coroutines-javafx").jvm
        val slf4j = artifact("kotlinx-coroutines-slf4j").jvm
        val core = artifact("kotlinx-coroutines").targets(common("core-common"), jvm("core"), android("android"))
        val core8 = core.targets(common("core-common"), jvm("jdk8"), android("android"))
    }

    val ktor by lazy(::Ktor)

    inner class Ktor : GroupWithVersionTargets by group("io.ktor").version(builtInVersions.ktor).targets(jvm("jvm"), common()) {
        val client by lazy(::Client)

        inner class Client {
            val core = artifact("ktor-client-core")
            val websockets = artifact("ktor-client-websockets")
            val cio = artifact("ktor-client-cio").jvm
        }
    }

    val koin by lazy(::Koin)

    inner class Koin : GroupWithVersionTargets by group("org.koin", "jcenter").version(builtInVersions.koin).jvm {
        val core = artifact("koin-core")
        val ext = artifact("koin-core-ext")
        val slf4j = artifact("koin-logger-slf4j")
    }

    val logging by lazy(::Logging)

    inner class Logging {
        val slf4j =
            group("org.slf4j", "mavenCentral")
                .artifact("slf4j-api")
                .version(builtInVersions.slf4j).jvm
        val julToSlf4j = slf4j.artifact("jul-to-slf4j")
        val logback =
            group("ch.qos.logback", "mavenCentral")
                .artifact("logback-classic")
                .version(builtInVersions.logback).jvm

        val logging =
            group("io.github.microutils", "mavenCentral")
                .artifact("kotlin-logging")
                .version(builtInVersions.microutilsLogging)
                .targets(common("common"), jvm())
    }

    val shadow =
        group("com.github.jengelman.gradle.plugins")
            .artifact("shadow", "mavenCentral")
            .version(builtInVersions.shadow).jvm
    val updates =
        group("com.github.ben-manes")
            .artifact("gradle-versions-plugin", "gradlePluginPortal")
            .version(builtInVersions.gradleVersions).jvm
    val docker =
        group("gradle.plugin.com.google.cloud.tools")
            .artifact("jib-gradle-plugin", "google")
            .version(builtInVersions.jib).jvm
    val detekt =
        group("io.gitlab.arturbosch.detekt")
            .artifact("detekt-gradle-plugin", "gradlePluginPortal")
            .version(builtInVersions.detekt).jvm
    val bintray =
        group("com.jfrog.bintray.gradle")
            .artifact("gradle-bintray-plugin", "jcenter")
            .version(builtInVersions.bintray).jvm
    val buildScan =
        group("com.gradle")
            .artifact("build-scan-plugin", "gradlePluginPortal")
            .version(builtInVersions.buildScan).jvm
    val androidPlugin =
        group("com.android.tools.build")
            .artifact("gradle", "google")
            .version(builtInVersions.androidPlugin).jvm

    val kamp =
        group("dev.whyoleg.kamp")
            .artifact("kamp", "https://dl.bintray.com/whyoleg/kamp")
            .version(builtInVersions.kamp).jvm
}
