package dev.whyoleg.kamp.builtin

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.classifier.*
import dev.whyoleg.kamp.target.*

object BuiltInDependencies {

    val kotlin by lazy(::Kotlin)

    open class Kotlin : GroupVersionClassifier, MavenCentralProviderClassifier {
        override val group: String = "org.jetbrains.kotlin"
        override val version: String = BuiltInVersions.kotlin

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

    open class Kotlinx : GroupClassifier, KotlinxProviderClassifier {
        override val group: String = "org.jetbrains.kotlinx"

        open val plugin by lazy(::Plugin)

        open inner class Plugin {
            open val atomicfu = raw("atomicfu-gradle-plugin", BuiltInVersions.atomicfu)
        }

        open val serialization = dependency("kotlinx-serialization-runtime", BuiltInVersions.serialization, jvm(), common("common"))
        open val atomicfu = dependency("atomicfu", BuiltInVersions.atomicfu, jvm(), common("common"))
    }

    val coroutines by lazy(::Coroutines)

    open class Coroutines : GroupVersionClassifier, KotlinxProviderClassifier {
        override val group: String = "org.jetbrains.kotlinx"
        override val version: String = BuiltInVersions.coroutines

        val javaFX = dependency("kotlinx-coroutines-javafx", jvm())
        val slf4j = dependency("kotlinx-coroutines-slf4j", jvm())
        val core = dependency("kotlinx-coroutines", common("core-common"), jvm8("jdk8"), jvm("core"), android("android"))
    }

    val ktor by lazy(::Ktor)

    open class Ktor : GroupVersionClassifier, KtorProviderClassifier, MultiTargetClassifier {
        override val group: String = "io.ktor"
        override val version: String = BuiltInVersions.ktor
        override val targets: Set<TargetWithPostfix<*>> = setOf(jvm("jvm"), common())

        val client by lazy(::Client)

        inner class Client {
            val core = dependency("ktor-client-core")
            val websockets = dependency("ktor-client-websockets")
            val cio = dependency("ktor-client-cio", jvm())
        }
    }

    val shadow = RawDependency("com.github.jengelman.gradle.plugins", "shadow", BuiltInVersions.shadow, DependencyProviders.gradlePluginPortal)
    val updates = RawDependency("com.github.ben-manes", "gradle-versions-plugin", BuiltInVersions.updates, DependencyProviders.gradlePluginPortal)
    val docker = RawDependency("gradle.plugin.com.google.cloud.tools", "jib-gradle-plugin", BuiltInVersions.docker, DependencyProviders.google)
    val detekt = RawDependency("io.gitlab.arturbosch.detekt", "detekt-gradle-plugin", BuiltInVersions.detekt, DependencyProviders.gradlePluginPortal)

    val kamp = RawDependency("dev.whyoleg.kamp", "kamp", "0.1.0", DependencyProviders.mavenLocal)
}