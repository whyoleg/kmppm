package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.version.*

object BuiltInDependencies {

    val kotlin by lazy(::Kotlin)

    class Kotlin : GroupVersionClassifier {
        override val group: String = "org.jetbrains.kotlin"
        override var version: String = BuiltInVersions.kotlin

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

    class Kotlinx : GroupClassifier {
        override val group: String = "org.jetbrains.kotlinx"

        val plugin by lazy(::Plugin)

        inner class Plugin {
            val atomicfu = raw("atomicfu-gradle-plugin", BuiltInVersions.atomicfu)
        }

        val coroutines by lazy(::Coroutines)

        inner class Coroutines : GroupClassifier by kotlinx, VersionClassifier {
            override var version: String = BuiltInVersions.coroutines

            val javaFX = dependency("kotlinx-coroutines-javafx", jvm())
            val slf4j = dependency("kotlinx-coroutines-slf4j", jvm())
            val core = dependency("kotlinx-coroutines", common("core-common"), jvm8("jdk8"), jvm("core"), android("android"))
        }

        val serialization = dependency("kotlinx-serialization-runtime", BuiltInVersions.serialization, jvm(), common("common"))
        val atomicfu = dependency("atomicfu", BuiltInVersions.atomicfu, jvm(), common("common"))
    }

    val ktor by lazy(::Ktor)

    class Ktor : GroupVersionClassifier, MultiTargetClassifier {
        override val group: String = "org.jetbrains.kotlinx"
        override var version: String = BuiltInVersions.ktor
        override val targets: Set<TargetWithPostfix<*>> = setOf(jvm("jvm"), common())

        val client by lazy(::Client)

        inner class Client {
            val core = dependency("ktor-client-core")
            val websockets = dependency("ktor-client-websockets")
            val cio = dependency("ktor-client-cio", jvm())
        }
    }

    val shadow = RawDependency("com.github.jengelman.gradle.plugins", "shadow", BuiltInVersions.shadow)
    val updates = RawDependency("com.github.ben-manes", "gradle-versions-plugin", BuiltInVersions.updates)
    val docker = RawDependency("gradle.plugin.com.google.cloud.tools", "jib-gradle-plugin", BuiltInVersions.docker)
    val detekt = RawDependency("io.gitlab.arturbosch.detekt", "detekt-gradle-plugin", BuiltInVersions.detekt)

}