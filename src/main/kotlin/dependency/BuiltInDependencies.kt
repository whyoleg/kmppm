package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.version.*

object BuiltInDependencies {
    object Kotlin : GroupVersionClassifier {
        override val group: String = "org.jetbrains.kotlin"
        override val version: String = BuiltInVersions.kotlin

        object Plugin {
            val gradle = raw("kotlin-gradle-plugin")
            val serialization = raw("kotlin-serialization")
        }

        val stdlib = dependency("kotlin-stdlib", jvm(), jvm8("jdk8"), common("common"))
        val test = dependency("kotlin-test", common("common"), jvm(), js("js"))
        val annotations = dependency("kotlin-test", common("annotations-common"), jvm("junit"))
    }

    object KotlinX : GroupClassifier {
        override val group: String = "org.jetbrains.kotlinx"

        object Plugin {
            val atomicfu = raw("atomicfu-gradle-plugin", BuiltInVersions.atomicfu)
        }

        val coroutines = dependency("kotlinx-coroutines", BuiltInVersions.coroutines, common("core-common"), jvm8("jdk8"), jvm("core"), android("android"))
        val serialization = dependency("kotlinx-serialization-runtime", BuiltInVersions.serialization, jvm(), common("common"))
        val atomicfu = dependency("atomicfu", BuiltInVersions.atomicfu, jvm(), common("common"))
    }

    val shadow = RawDependency("com.github.jengelman.gradle.plugins", "shadow", BuiltInVersions.shadow)
    val updates = RawDependency("com.github.ben-manes", "gradle-versions-plugin", BuiltInVersions.updates)
    val docker = RawDependency("gradle.plugin.com.google.cloud.tools", "jib-gradle-plugin", BuiltInVersions.docker)
    val detekt = RawDependency("io.gitlab.arturbosch.detekt", "detekt-gradle-plugin", BuiltInVersions.detekt)

}