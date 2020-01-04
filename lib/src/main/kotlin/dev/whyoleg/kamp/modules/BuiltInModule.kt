package dev.whyoleg.kamp.modules

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.builder.*

data class BuiltInVersions(
    val kamp: String,
    val gradleVersions: String,
    val jib: String,
    val shadow: String,
    val detekt: String,
    val bintray: String,
    val buildScan: String,
    val androidPlugin: String
) {
    companion object {
        val Stable: BuiltInVersions = BuiltInVersions(
            kamp = "0.2.0",
            gradleVersions = "0.27.0",
            jib = "1.6.1",
            shadow = "5.2.0",
            detekt = "1.1.1",
            bintray = "1.8.4",
            buildScan = "3.1.1",
            androidPlugin = "3.5.0"
        )
    }
}

class BuiltInModule(builtInVersions: BuiltInVersions) {
    companion object {
        val Stable: BuiltInModule by lazy { BuiltInModule(BuiltInVersions.Stable) }
    }

    val dependencies = BuiltInDependencies(builtInVersions)
    val plugins = BuiltInPlugins(dependencies)
}

class BuiltInDependencies(builtInVersions: BuiltInVersions) {
    companion object {
        val Stable: BuiltInDependencies by lazy { BuiltInModule.Stable.dependencies }
    }

    val shadow =
        group("com.github.jengelman.gradle.plugins")
            .artifact("shadow", RepositoryProviders.gradlePluginPortal)
            .version(builtInVersions.shadow).jvm

    val bintray =
        group("com.jfrog.bintray.gradle")
            .artifact("gradle-bintray-plugin", RepositoryProviders.jcenter)
            .version(builtInVersions.bintray).jvm

    val gradleVersions =
        group("com.github.ben-manes")
            .artifact("gradle-versions-plugin", RepositoryProviders.gradlePluginPortal)
            .version(builtInVersions.gradleVersions).jvm
    val detekt =
        group("io.gitlab.arturbosch.detekt")
            .artifact("detekt-gradle-plugin", RepositoryProviders.gradlePluginPortal)
            .version(builtInVersions.detekt).jvm
    val buildScan =
        group("com.gradle")
            .artifact("build-scan-plugin", RepositoryProviders.gradlePluginPortal)
            .version(builtInVersions.buildScan).jvm

    val jib =
        group("gradle.plugin.com.google.cloud.tools")
            .artifact("jib-gradle-plugin", RepositoryProviders.google)
            .version(builtInVersions.jib).jvm
    val androidPlugin =
        group("com.android.tools.build")
            .artifact("gradle", RepositoryProviders.google)
            .version(builtInVersions.androidPlugin).jvm

    val kamp =
        group("dev.whyoleg.kamp")
            .artifact("kamp", RepositoryProviders.mavenCentral, RepositoryProviders.bintray("whyoleg", "kamp"))
            .version(builtInVersions.kamp).jvm
}

class BuiltInPlugins(dependencies: BuiltInDependencies) {
    companion object {
        val Stable: BuiltInPlugins by lazy { BuiltInModule.Stable.plugins }
    }

    val shadow = KampPlugin("com.github.johnrengelman.shadow", dependencies.shadow)
    val gradleVersions = KampPlugin("com.github.ben-manes.versions", dependencies.gradleVersions)

    val jib = KampPlugin("com.google.cloud.tools.jib", dependencies.jib)
    val detekt = KampPlugin("io.gitlab.arturbosch.detekt", dependencies.detekt)
    val bintray = KampPlugin("com.jfrog.bintray", dependencies.bintray)
    val buildScan = KampPlugin("com.gradle.build-scan", dependencies.buildScan)

    val androidLib = KampPlugin("com.android.library", dependencies.androidPlugin)

    val kamp = KampPlugin("dev.whyoleg.kamp", dependencies.kamp)
}

