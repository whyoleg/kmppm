package dev.whyoleg.kamp.modules

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.options.*
import dev.whyoleg.kamp.platform.KampPlatform.*

inline class KotlinVersion(val value: String) {
    companion object {
        val Stable = KotlinVersion("1.3.61")
        val Eap = KotlinVersion("1.3.70-eap-42")
    }
}

class KotlinModule(version: KotlinVersion) {
    val dependencies = KotlinDependencies(version)
    val plugins = KotlinPlugins(dependencies)

    companion object {
        val Stable: KotlinModule by lazy { KotlinModule(KotlinVersion.Stable) }
        val EapProvider = RepositoryProviders.maven("https://dl.bintray.com/kotlin/kotlin-eap")
        val DevProvider = RepositoryProviders.maven("https://dl.bintray.com/kotlin/kotlin-dev")
    }
}

class KotlinDependencies(version: KotlinVersion) :
    GroupWithVersion by group("org.jetbrains.kotlin", RepositoryProviders.mavenCentral).version(version.value) {
    companion object {
        val Stable: KotlinDependencies by lazy { KotlinModule.Stable.dependencies }
    }

    val gradlePlugin = artifact("kotlin-gradle-plugin").jvm
    val serializationPlugin = artifact("kotlin-serialization").jvm

    val stdlib = artifact("kotlin-stdlib").platforms(common("common"), jvm(), js("js"))
    val stdlib8 = stdlib.platforms(common("common"), jvm("jdk8"), js("js"))
    val test = stdlib.artifact("kotlin-test")
    val annotations = test.platforms(common("annotations-common"), jvm("junit"))
    val reflect = artifact("kotlin-reflect").jvm
}

class KotlinPlugins(dependencies: KotlinDependencies) {
    companion object {
        val Stable: KotlinPlugins by lazy { KotlinModule.Stable.plugins }
    }

    val kotlinMpp = KampPlugin("kotlin-multiplatform", dependencies.gradlePlugin)
    val kotlinJvm = KampPlugin("org.jetbrains.kotlin.jvm", dependencies.gradlePlugin)
    val serialization = KampPlugin("kotlinx-serialization", dependencies.serializationPlugin)
}

object KotlinExperimentalAnnotations {
    val Experimental = ExperimentalAnnotation("kotlin.Experimental")
    val ExperimentalStdlibApi = ExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
    val ExperimentalUnsignedTypes = ExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes")
    val ExperimentalContracts = ExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
    val ExperimentalTime = ExperimentalAnnotation("kotlin.time.ExperimentalTime")

    val All = listOf(
        Experimental,
        ExperimentalStdlibApi,
        ExperimentalUnsignedTypes,
        ExperimentalContracts,
        ExperimentalTime
    )
}
