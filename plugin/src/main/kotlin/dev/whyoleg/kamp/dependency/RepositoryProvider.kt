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
    fun bintray(org: String, user: String, repo: String): RepositoryProvider = maven { setUrl("https://$org.bintray.com/$user/$repo") }


    fun bintray(user: String, repo: String): RepositoryProvider = bintray("dl", user, repo)

    fun bintrayPublish(
        user: String,
        repo: String,
        name: String,
        bintrayUser: String,
        bintrayApiKey: String,
        publish: Boolean = false,
        override: Boolean = false
    ): RepositoryProvider = maven {
        setUrl("https://api.bintray.com/maven/$user/$repo/$name/;publish=${if (publish) 1 else 0};override=${if (override) 1 else 0}")
        credentials {
            it.username = bintrayUser
            it.password = bintrayApiKey
        }
    }

    fun bintrayPublish(
        user: String,
        repo: String,
        name: String,
        publish: Boolean = false,
        override: Boolean = false
    ): RepositoryProvider = maven {
        setUrl("https://api.bintray.com/maven/$user/$repo/$name/;publish=${if (publish) 1 else 0};override=${if (override) 1 else 0}")
        credentials {
            it.username = System.getenv("BINTRAY_USER")
            it.password = System.getenv("BINTRAY_API_KEY")
        }
    }
}