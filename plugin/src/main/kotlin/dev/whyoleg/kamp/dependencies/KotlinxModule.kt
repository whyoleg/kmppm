package dev.whyoleg.kamp.dependencies

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.platform.KampPlatform.*

data class KotlinxVersions(
    val coroutines: String,
    val serialization: String,
    val atomicfu: String,
    val cli: String
) {
    companion object {
        val Stable = KotlinxVersions(
            coroutines = "1.3.3",
            serialization = "0.14.0",
            atomicfu = "0.14.1",
            cli = "0.2.0-dev-7"
        )
    }
}

class KotlinxModule(versions: KotlinxVersions) {
    val dependencies = KotlinxDependencies(versions)
    val plugins = KotlinxPlugins(dependencies)
}

class KotlinxDependencies(private val versions: KotlinxVersions) :
    GroupWithPlatforms by group("org.jetbrains.kotlinx", "mavenCentral").platforms(common("common"), jvm(), js("js")) {

    val serialization by lazy(::Serialization)

    inner class Serialization : GroupWithVersionPlatforms by version(versions.serialization) {
        val runtime = artifact("kotlinx-serialization-runtime")
        val configParser = artifact("kotlinx-serialization-runtime-configparser").jvm
    }

    val atomicfu by lazy(::Atomicfu)

    inner class Atomicfu : GroupWithVersionPlatforms by version(versions.atomicfu) {
        val atomicfu = artifact("atomicfu") //TODO name
        val plugin = artifact("atomicfu-gradle-plugin").jvm
    }

    val coroutines by lazy(::Coroutines)

    inner class Coroutines : GroupWithVersion by version(versions.coroutines) {
        val javaFX = artifact("kotlinx-coroutines-javafx").jvm
        val slf4j = artifact("kotlinx-coroutines-slf4j").jvm
        val core = artifact("kotlinx-coroutines").platforms(common("core-common"), jvm("core"), android("android"))
        val core8 = core.platforms(common("core-common"), jvm("jdk8"), android("android"))
    }

    val cli = artifact("kotlinx-cli").version(versions.cli).platforms(common(), jvm("jvm"), js("js"))
}

class KotlinxPlugins(dependencies: KotlinxDependencies) {
    val atomicfu by lazy { KampPlugin("kotlinx-atomicfu", dependencies.atomicfu.plugin) }
}
