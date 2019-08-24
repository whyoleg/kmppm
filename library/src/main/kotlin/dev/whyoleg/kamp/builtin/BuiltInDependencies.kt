package dev.whyoleg.kamp.builtin

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.classifier.*
import dev.whyoleg.kamp.target.*

open class BuiltInDependencies {
    companion object : BuiltInDependencies()

    val kotlin by lazy(::Kotlin)

    inner class Kotlin : GroupVersionClassifier, MavenCentralProviderClassifier {
        override val group: String = "org.jetbrains.kotlin"
        override val version: (BuiltInVersions) -> String = BuiltInVersions::kotlin

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
            val atomicfu = raw("atomicfu-gradle-plugin", BuiltInVersions::atomicfu)
        }

        val serialization = dependency("kotlinx-serialization-runtime", BuiltInVersions::serialization, jvm(), common("common"))
        val atomicfu = dependency("atomicfu", BuiltInVersions::atomicfu, jvm(), common("common"))

        val configParser = dependency("kotlinx-serialization-runtime-configparser", BuiltInVersions::serialization, jvmOnly.postfixed())
    }

    val coroutines by lazy(::Coroutines)

    inner class Coroutines : GroupVersionClassifier, KotlinxProviderClassifier {
        override val group: String = "org.jetbrains.kotlinx"
        override val version: (BuiltInVersions) -> String = BuiltInVersions::coroutines

        val javaFX = dependency("kotlinx-coroutines-javafx", jvm())
        val slf4j = dependency("kotlinx-coroutines-slf4j", jvm())
        val core = dependency("kotlinx-coroutines", common("core-common"), jvm8("jdk8"), jvm("core"), android("android"))
    }

    val ktor by lazy(::Ktor)

    inner class Ktor : GroupVersionClassifier, KtorProviderClassifier, MultiTargetClassifier {
        override val group: String = "io.ktor"
        override val version: (BuiltInVersions) -> String = BuiltInVersions::ktor
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
        override var version: (BuiltInVersions) -> String = BuiltInVersions::koin
        override val targets: Set<TargetWithPostfix<JvmBasedTarget>> = jvmBased.postfixed()

        val core = dependency("koin-core")
        val ext = dependency("koin-core-ext")
        val slf4j = dependency("koin-logger-slf4j")
    }

    val logging by lazy(::Logging)

    inner class Logging : TargetClassifier<JvmBasedTarget> {
        override val targets: Set<TargetWithPostfix<JvmBasedTarget>> = jvmBased.postfixed()

        val slf4j = dependency("org.slf4j", "slf4j-api", BuiltInVersions::slf4j, DependencyProviders.mavenCentral)
        val julToSlf4j = dependency("org.slf4j", "jul-to-slf4j", BuiltInVersions::slf4j, DependencyProviders.mavenCentral)
        val logback = dependency("ch.qos.logback", "logback-classic", BuiltInVersions::logback, DependencyProviders.mavenCentral)

        val logging = RawDependency(
            "io.github.microutils",
            "kotlin-logging",
            BuiltInVersions::logging,
            DependencyProviders.mavenCentral
        )(common("common"), jvm())
    }

    val shadow =
        RawDependency("com.github.jengelman.gradle.plugins", "shadow", BuiltInVersions::shadow, DependencyProviders.gradlePluginPortal)
    val updates =
        RawDependency("com.github.ben-manes", "gradle-versions-plugin", BuiltInVersions::updates, DependencyProviders.gradlePluginPortal)
    val docker =
        RawDependency("gradle.plugin.com.google.cloud.tools", "jib-gradle-plugin", BuiltInVersions::docker, DependencyProviders.google)
    val detekt = RawDependency(
        "io.gitlab.arturbosch.detekt",
        "detekt-gradle-plugin",
        BuiltInVersions::detekt,
        DependencyProviders.gradlePluginPortal
    )
    val versioning =
        RawDependency("gradle.plugin.net.nemerosa", "versioning", BuiltInVersions::versioning, DependencyProviders.gradlePluginPortal)
    val bintray = RawDependency("com.jfrog.bintray.gradle", "gradle-bintray-plugin", BuiltInVersions::bintray, DependencyProviders.jcenter)

    val kamp =
        RawDependency("dev.whyoleg.kamp", "kamp", BuiltInVersions::kamp, DependencyProviders.maven("https://dl.bintray.com/whyoleg/kamp"))
}