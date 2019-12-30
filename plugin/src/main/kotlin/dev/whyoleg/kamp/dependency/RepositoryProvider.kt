package dev.whyoleg.kamp.dependency

import org.gradle.api.artifacts.dsl.*

typealias RepositoryProvider = RepositoryHandler.() -> Unit

operator fun RepositoryProvider.plus(anotherProvider: RepositoryProvider): RepositoryProvider = {
    this@plus()
    anotherProvider()
}

fun List<RepositoryProvider>.sum(): RepositoryProvider = {
    this@sum.forEach { it.invoke(this) }
}

object RepositoryProviders {
    val google: RepositoryProvider = { google() }
    val mavenLocal: RepositoryProvider = { mavenLocal() }
    val mavenCentral: RepositoryProvider = { mavenCentral() }
    val jcenter: RepositoryProvider = { jcenter() }
    val jitpack: RepositoryProvider = maven("https://jitpack.io")
    @Suppress("UnstableApiUsage")
    val gradlePluginPortal: RepositoryProvider = { gradlePluginPortal() }

    fun maven(url: String): RepositoryProvider = { maven { it.setUrl(url) } }
}
