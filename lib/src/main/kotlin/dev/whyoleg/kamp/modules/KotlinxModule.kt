package dev.whyoleg.kamp.modules

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.options.*
import dev.whyoleg.kamp.platform.*
import dev.whyoleg.kamp.platform.KampPlatform.*

data class KotlinxVersions(
    val coroutines: String,
    val serialization: String,
    val atomicfu: String,
    val cli: String,
    val immutableCollections: String
) {
    companion object {
        val Stable = KotlinxVersions(
            coroutines = "1.3.3",
            serialization = "0.14.0",
            atomicfu = "0.14.1",
            cli = "0.2.0-dev-7",
            immutableCollections = "0.3"
        )
    }
}

class KotlinxModule(versions: KotlinxVersions) {
    val dependencies = KotlinxDependencies(versions)
    val plugins = KotlinxPlugins(dependencies)

    companion object {
        val Provider = RepositoryProviders.bintrayOrg("kotlin", "kotlinx")
        val Stable: KotlinxModule by lazy { KotlinxModule(KotlinxVersions.Stable) }
    }
}

class KotlinxDependencies(private val versions: KotlinxVersions) : Group by group("org.jetbrains.kotlinx", KotlinxModule.Provider) {
    companion object {
        val Stable: KotlinxDependencies by lazy { KotlinxModule.Stable.dependencies }
    }

    val serialization by lazy(::Serialization)

    inner class Serialization : GroupWithVersionPlatforms by version(versions.serialization).platforms(KampPlatform.oldStyle()) {
        val runtime = artifact("kotlinx-serialization-runtime")
        val configParser = artifact("kotlinx-serialization-runtime-configparser").jvm
    }

    val atomicfu by lazy(::Atomicfu)

    inner class Atomicfu : GroupWithVersionPlatforms by version(versions.atomicfu).platforms(KampPlatform.oldStyle()) {
        val runtime = artifact("atomicfu")
        val plugin = artifact("atomicfu-gradle-plugin").jvm
    }

    val coroutines by lazy(::Coroutines)

    inner class Coroutines : GroupWithVersion by version(versions.coroutines) {
        val core = artifact("kotlinx-coroutines-core").platforms(KampPlatform.oldStyle())

        val test = artifact("kotlinx-coroutines-test").jvm
        val debug = artifact("kotlinx-coroutines-debug").jvm

        val ui by lazy(::UI)

        inner class UI {
            val android = artifact("kotlinx-coroutines-android").android
            val javaFX = artifact("kotlinx-coroutines-javafx").jvm
            val swing = artifact("kotlinx-coroutines-swing").jvm
        }

        val integration by lazy(::Integration)

        inner class Integration : GroupWithVersionPlatforms by jvm {
            val slf4j = artifact("kotlinx-coroutines-slf4j")
            val playServices = artifact("kotlinx-coroutines-play-services")
            val guava = artifact("kotlinx-coroutines-guava")
            val jdk8 = artifact("kotlinx-coroutines-jdk8")
        }

        val reactive by lazy(::Reactive)

        inner class Reactive : GroupWithVersionPlatforms by jvm {
            val reactiveStreams = artifact("kotlinx-coroutines-reactive")
            val reactor = artifact("kotlinx-coroutines-reactor")
            val rx2 = artifact("kotlinx-coroutines-rx2")
        }
    }

    val cli by lazy(::Cli)

    inner class Cli : GroupWithVersionArtifact by artifact("kotlinx-cli", KotlinModule.DevProvider).version(versions.cli) {
        val metadata = platforms(common())
        val jvm = platforms(jvm("jvm"))
    }

    val immutableCollections by lazy(::ImmutableCollections)

    inner class ImmutableCollections :
        GroupWithVersionArtifact by artifact("kotlinx-collections-immutable").version(versions.immutableCollections) {
        val metadata = platforms(common())
        val jvm = platforms(jvm("jvm"))
    }
}

class KotlinxPlugins(dependencies: KotlinxDependencies) {
    companion object {
        val Stable: KotlinxPlugins by lazy { KotlinxModule.Stable.plugins }
    }

    val atomicfu by lazy { KampPlugin("kotlinx-atomicfu", dependencies.atomicfu.plugin) }
}

object KotlinxExperimentalAnnotations {
    val ObsoleteCoroutinesApi = ExperimentalAnnotation("kotlinx.coroutines.ObsoleteCoroutinesApi")
    val ExperimentalCoroutinesApi = ExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
    val InternalCoroutinesApi = ExperimentalAnnotation("kotlinx.coroutines.InternalCoroutinesApi")
    val FlowPreview = ExperimentalAnnotation("kotlinx.coroutines.FlowPreview")
    val ImplicitReflectionSerializer = ExperimentalAnnotation("kotlinx.serialization.ImplicitReflectionSerializer")

    val All = listOf(
        ObsoleteCoroutinesApi,
        ExperimentalCoroutinesApi,
        InternalCoroutinesApi,
        FlowPreview,
        ImplicitReflectionSerializer
    )

}
