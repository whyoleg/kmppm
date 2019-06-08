package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.version.*

object BuiltInDependencies {
    object Kotlin : Group("org.jetbrains.kotlin") {
        object Plugin {
            val gradle = dependency("kotlin-gradle-plugin", BuiltInVersions.kotlin)
            val serialization = dependency("kotlin-serialization", BuiltInVersions.kotlin)
        }

//        val stdlib = dependency("kotlin-stdlib", BuiltInVersions.kotlin)
//        val coroutines = dependency("kotlin-stdlib", BuiltInVersions.kotlin)
//        val serialization = dependency("kotlin-stdlib", BuiltInVersions.kotlin)
    }

    object Kotlinx : Group("org.jetbrains.kotlinx") {
        object Plugin {
            val atomicfu = dependency("atomicfu-gradle-plugin", BuiltInVersions.atomicfu)
        }
    }

    val shadow = Group("com.github.jengelman.gradle.plugins").dependency("shadow", BuiltInVersions.shadow)
    val updates = Group("com.github.ben-manes").dependency("gradle-versions-plugin", BuiltInVersions.updates)
    val docker = Group("gradle.plugin.com.google.cloud.tools").dependency("jib-gradle-plugin", BuiltInVersions.docker)

}