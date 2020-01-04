package dev.whyoleg.kamp.dependency

import org.gradle.api.artifacts.dsl.*
import org.gradle.api.artifacts.repositories.*

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

    fun maven(block: MavenArtifactRepository.() -> Unit): RepositoryProvider = { maven(block) }
    fun maven(url: String): RepositoryProvider = maven { setUrl(url) }
    fun bintray(user: String, repo: String): RepositoryProvider = maven("https://dl.bintray.com/$user/$repo")
    fun bintrayOrg(org: String, repo: String): RepositoryProvider = maven("https://$org.bintray.com/$repo")

    fun bintrayPublish(
        user: String,
        repo: String,
        name: String,
        publish: Boolean = false,
        override: Boolean = false,
        bintrayUser: String? = null,
        bintrayApiKey: String? = null
    ): RepositoryProvider = maven {
        setUrl("https://api.bintray.com/maven/$user/$repo/$name/;publish=${if (publish) 1 else 0};override=${if (override) 1 else 0}")
        credentials {
            it.username = bintrayUser ?: System.getenv("BINTRAY_USER")
            it.password = bintrayApiKey ?: System.getenv("BINTRAY_API_KEY")
        }
    }
}
