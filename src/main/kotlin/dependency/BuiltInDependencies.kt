package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.version.*

object BuiltInDependencies {
    object Kotlin : GroupVersionClassifier, MultiTargetClassifier {
        override val group: String = "org.jetbrains.kotlin"
        override val version: String = BuiltInVersions.kotlin
        override val targets: Set<TargetWithPostfix<*>> = setOf(jvm(), common())

        object Plugin {
            val gradle = raw("kotlin-gradle-plugin")
            val serialization = raw("kotlin-serialization")
        }

        val stdlib = dependency("kotlin-stdlib")
//        val coroutines = dependency("kotlin-stdlib", BuiltInVersions.kotlin)
//        val serialization = dependency("kotlin-stdlib", BuiltInVersions.kotlin)
    }

    object KotlinX : GroupClassifier {
        override val group: String = "org.jetbrains.kotlinx"

        object Plugin {
            val atomicfu = raw("atomicfu-gradle-plugin", BuiltInVersions.atomicfu)
        }
    }

    val shadow = RawDependency("com.github.jengelman.gradle.plugins", "shadow", BuiltInVersions.shadow)
    val updates = RawDependency("com.github.ben-manes", "gradle-versions-plugin", BuiltInVersions.updates)
    val docker = RawDependency("gradle.plugin.com.google.cloud.tools", "jib-gradle-plugin", BuiltInVersions.docker)
    val detekt = RawDependency("io.gitlab.arturbosch.detekt", "detekt-gradle-plugin", BuiltInVersions.detekt)

}