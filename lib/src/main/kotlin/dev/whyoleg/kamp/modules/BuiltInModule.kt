package dev.whyoleg.kamp.modules

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.platform.KampPlatform.*

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
            kamp = "0.2.1-pre-5",
            gradleVersions = "0.27.0",
            jib = "1.6.1",
            shadow = "5.2.0",
            detekt = "1.1.1",
            bintray = "1.8.4",
            buildScan = "3.1.1",
            androidPlugin = "3.5.3"
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
        dependency("com.github.jengelman.gradle.plugins", "shadow", builtInVersions.shadow, jvm(), RepositoryProviders.gradlePluginPortal)
    val bintray =
        dependency("com.jfrog.bintray.gradle", "gradle-bintray-plugin", builtInVersions.bintray, jvm(), RepositoryProviders.jcenter)
    val gradleVersions =
        dependency("com.github.ben-manes", "gradle-versions-plugin", builtInVersions.gradleVersions, jvm(), RepositoryProviders.gradlePluginPortal)
    val detekt =
        dependency("io.gitlab.arturbosch.detekt", "detekt-gradle-plugin", builtInVersions.detekt, jvm(), RepositoryProviders.gradlePluginPortal)
    val buildScan =
        dependency("com.gradle", "build-scan-plugin", builtInVersions.buildScan, jvm(), RepositoryProviders.gradlePluginPortal)
    val jib =
        dependency("gradle.plugin.com.google.cloud.tools", "jib-gradle-plugin", builtInVersions.jib, jvm(), RepositoryProviders.google)
    val androidPlugin =
        dependency("com.android.tools.build", "gradle", builtInVersions.androidPlugin, jvm(), RepositoryProviders.google)
    val kamp =
        dependency("dev.whyoleg.kamp", "kamp", builtInVersions.kamp, jvm(), RepositoryProviders.bintray("whyoleg", "kamp"), RepositoryProviders.mavenLocal)
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

