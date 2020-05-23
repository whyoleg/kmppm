package dev.whyoleg.kamp.feature.ktor

import dev.whyoleg.kamp.dependency.*

object Ktor {
    val defaultVersion = "1.3.2"
    fun dependencies(version: String = defaultVersion): KtorDependencies = KtorDependencies(version)

    val optIn = KtorOptIn
    val repository = RepositoryProviders.bintray("kotlin", "ktor")
}
