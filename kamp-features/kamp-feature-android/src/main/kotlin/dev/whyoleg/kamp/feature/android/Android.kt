package dev.whyoleg.kamp.feature.android

object Android {
    val defaultVersion = AndroidVersions.Default
    val plugins = AndroidPlugins
    fun dependencies(versions: AndroidVersions = defaultVersion): AndroidDependencies = AndroidDependencies(versions)
}
