package dev.whyoleg.kamp.dependencies

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
        val stable: BuiltInVersions = BuiltInVersions(
            kamp = "0.1.16",
            gradleVersions = "0.26.0",
            jib = "1.6.1",
            shadow = "5.1.0",
            detekt = "1.1.1",
            bintray = "1.8.4",
            buildScan = "2.4.2",
            androidPlugin = "3.5.0"
        )
    }
}

class BuiltInModule(builtInVersions: BuiltInVersions) {
    val dependencies = BuiltInDependencies(builtInVersions)
    val plugins = BuiltInPlugins(dependencies)
}

class BuiltInDependencies(builtInVersions: BuiltInVersions) {

    val shadow =
        group("com.github.jengelman.gradle.plugins")
            .artifact("shadow", RepositoryProviders.mavenCentral)
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
            .artifact("kamp", RepositoryProviders.maven("https://dl.bintray.com/whyoleg/kamp"))
            .version(builtInVersions.kamp).jvm
}

class BuiltInPlugins(dependencies: BuiltInDependencies) {

    val shadow = KampPlugin("com.github.johnrengelman.shadow", dependencies.shadow)
    val gradleVersions = KampPlugin("com.github.ben-manes.versions", dependencies.gradleVersions)

    val jib = KampPlugin("com.google.cloud.tools.jib", dependencies.jib)
    val detekt = KampPlugin("io.gitlab.arturbosch.detekt", dependencies.detekt)
    val bintray = KampPlugin("com.jfrog.bintray", dependencies.bintray)
    val buildScan = KampPlugin("com.gradle.build-scan", dependencies.buildScan)

    val androidLib = KampPlugin("com.android.library", dependencies.androidPlugin)

    internal val kamp = KampPlugin("dev.whyoleg.kamp", dependencies.kamp)
}

