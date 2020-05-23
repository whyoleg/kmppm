package dev.whyoleg.kamp.feature.kotlin

import dev.whyoleg.kamp.dependency.*

object Kotlin {
    val defaultVersion = "1.3.72"
    fun dependencies(version: String = defaultVersion): KotlinDependencies = KotlinDependencies(version)

    val plugins = KotlinPlugins
    val optIn = KotlinOptIn

    val eapRepository = RepositoryProviders.bintray("kotlin", "kotlin-eap")
    val devRepository = RepositoryProviders.bintray("kotlin", "kotlin-dev")
}
