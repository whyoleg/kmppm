package dev.whyoleg.kamp.dependencies

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.platform.KampPlatform.*

inline class KotlinVersion(val value: String) {
    companion object {
        val stable = KotlinVersion("1.3.61")
        val eap = KotlinVersion("1.3.70-eap-42")
    }
}

class KotlinModule(version: KotlinVersion) {
    val dependencies = KotlinDependencies(version)
    val plugins = KotlinPlugins(dependencies)

    companion object {
        val eapProvider = RepositoryProviders.maven("https://dl.bintray.com/kotlin/kotlin-eap")
        val devProvider = RepositoryProviders.maven("https://dl.bintray.com/kotlin/kotlin-dev")
    }
}

class KotlinDependencies(version: KotlinVersion) :
    GroupWithVersion by group("org.jetbrains.kotlin", RepositoryProviders.mavenCentral).version(version.value) {

    val gradlePlugin = artifact("kotlin-gradle-plugin").jvm
    val serializationPlugin = artifact("kotlin-serialization").jvm

    val stdlib = artifact("kotlin-stdlib").platforms(common("common"), jvm(), js("js"))
    val stdlib8 = stdlib.platforms(common("common"), jvm("jdk8"), js("js"))
    val test = stdlib.artifact("kotlin-test")
    val annotations = test.platforms(common("annotations-common"), jvm("junit"))
    val reflect = artifact("kotlin-reflect").jvm
}

class KotlinPlugins(dependencies: KotlinDependencies) {
    val kotlinMpp = KampPlugin("kotlin-multiplatform", dependencies.gradlePlugin)
    val kotlinJvm = KampPlugin("org.jetbrains.kotlin.jvm", dependencies.gradlePlugin)
    val serialization = KampPlugin("kotlinx-serialization", dependencies.serializationPlugin)
}
