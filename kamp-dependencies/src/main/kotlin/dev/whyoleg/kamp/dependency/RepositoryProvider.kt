package dev.whyoleg.kamp.dependency

import org.gradle.api.artifacts.dsl.*
import org.gradle.api.artifacts.repositories.*

typealias RepositoryProvider = RepositoryHandler.() -> Unit

object RepositoryProviders {
    val google: RepositoryProvider = { google() }
    val mavenLocal: RepositoryProvider = { mavenLocal() }
    val mavenCentral: RepositoryProvider = { mavenCentral() }
    val jcenter: RepositoryProvider = { jcenter() }
    val jitpack: RepositoryProvider = maven("https://jitpack.io")
    val gradlePluginPortal: RepositoryProvider = { gradlePluginPortal() }

    fun maven(block: MavenArtifactRepository.() -> Unit): RepositoryProvider = { maven(block) }
    fun maven(url: String): RepositoryProvider = maven { setUrl(url) }
    fun bintray(user: String, repo: String): RepositoryProvider = maven("https://dl.bintray.com/$user/$repo")
    fun bintrayOrg(org: String, repo: String): RepositoryProvider = maven("https://$org.bintray.com/$repo")
}
