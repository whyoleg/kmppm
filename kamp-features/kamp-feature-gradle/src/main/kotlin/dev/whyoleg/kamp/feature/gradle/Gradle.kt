package dev.whyoleg.kamp.feature.gradle

object Gradle {
    val defaultVersions = GradleVersions.Default
    fun dependencies(versions: GradleVersions = defaultVersions): GradleDependencies = GradleDependencies(versions)

    val plugins = GradlePlugins
}
