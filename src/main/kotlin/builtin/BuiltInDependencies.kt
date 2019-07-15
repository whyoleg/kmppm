package dev.whyoleg.kamp.builtin

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.classifier.*
import dev.whyoleg.kamp.target.*

open class BuiltInDependencies(private val versions: BuiltInVersions) {

    val kotlin by lazy(::Kotlin)

    inner class Kotlin : GroupVersionClassifier, MavenCentralProviderClassifier {
        override val group: String = "org.jetbrains.kotlin"
        override val version: String = versions.kotlin

        val plugin by lazy(::Plugin)

        inner class Plugin {
            val gradle = raw("kotlin-gradle-plugin")
            val serialization = raw("kotlin-serialization")
        }

        val stdlib = dependency("kotlin-stdlib", jvm(), jvm8("jdk8"), common("common"))
        val test = dependency("kotlin-test", common("common"), jvm(), js("js"))
        val annotations = dependency("kotlin-test", common("annotations-common"), jvm("junit"))
        val reflect = dependency("kotlin-reflect", jvm())
    }

    val kotlinx by lazy(::Kotlinx)

    inner class Kotlinx : GroupClassifier, KotlinxProviderClassifier {
        override val group: String = "org.jetbrains.kotlinx"

        val plugin by lazy(::Plugin)

        inner class Plugin {
            val atomicfu = raw("atomicfu-gradle-plugin", versions.atomicfu)
        }

        val serialization = dependency("kotlinx-serialization-runtime", versions.serialization, jvm(), common("common"))
        val atomicfu = dependency("atomicfu", versions.atomicfu, jvm(), common("common"))

        val configParser = dependency("kotlinx-serialization-runtime-configparser", versions.serialization, jvmOnly.postfixed())
    }

    val coroutines by lazy(::Coroutines)

    inner class Coroutines : GroupVersionClassifier, KotlinxProviderClassifier {
        override val group: String = "org.jetbrains.kotlinx"
        override val version: String = versions.coroutines

        val javaFX = dependency("kotlinx-coroutines-javafx", jvm())
        val slf4j = dependency("kotlinx-coroutines-slf4j", jvm())
        val core = dependency("kotlinx-coroutines", common("core-common"), jvm8("jdk8"), jvm("core"), android("android"))
    }

    val ktor by lazy(::Ktor)

    inner class Ktor : GroupVersionClassifier, KtorProviderClassifier, MultiTargetClassifier {
        override val group: String = "io.ktor"
        override val version: String = versions.ktor
        override val targets: Set<TargetWithPostfix<*>> = setOf(jvm("jvm"), common())

        val client by lazy(::Client)

        inner class Client {
            val core = dependency("ktor-client-core")
            val websockets = dependency("ktor-client-websockets")
            val cio = dependency("ktor-client-cio", jvm())
        }
    }

    val koin by lazy(::Koin)

    inner class Koin : GroupVersionClassifier, TargetClassifier<JvmBasedTarget>, JcenterProviderClassifier {
        override val group: String = "org.koin"
        override var version: String = versions.koin
        override val targets: Set<TargetWithPostfix<JvmBasedTarget>> = jvmBased.postfixed()

        val core = dependency("koin-core")
        val ext = dependency("koin-core-ext")
        val slf4j = dependency("koin-logger-slf4j")
    }

    val logging by lazy(::Logging)

    inner class Logging : TargetClassifier<JvmBasedTarget> {
        override val targets: Set<TargetWithPostfix<JvmBasedTarget>> = jvmBased.postfixed()

        val slf4j = dependency("org.slf4j", "slf4j-api", versions.slf4j, DependencyProviders.mavenCentral)
        val logback = dependency("ch.qos.logback", "logback-classic", versions.logback, DependencyProviders.mavenCentral)

        val logging = RawDependency(
            "io.github.microutils",
            "kotlin-logging",
            versions.logging,
            DependencyProviders.mavenCentral
        )(common("common"), jvm())
    }

    val shadow = RawDependency("com.github.jengelman.gradle.plugins", "shadow", versions.shadow, DependencyProviders.gradlePluginPortal)
    val updates = RawDependency("com.github.ben-manes", "gradle-versions-plugin", versions.updates, DependencyProviders.gradlePluginPortal)
    val docker = RawDependency("gradle.plugin.com.google.cloud.tools", "jib-gradle-plugin", versions.docker, DependencyProviders.google)
    val detekt = RawDependency("io.gitlab.arturbosch.detekt", "detekt-gradle-plugin", versions.detekt, DependencyProviders.gradlePluginPortal)

    val kamp = RawDependency("dev.whyoleg.kamp", "kamp", "0.1.0", DependencyProviders.mavenLocal)
}