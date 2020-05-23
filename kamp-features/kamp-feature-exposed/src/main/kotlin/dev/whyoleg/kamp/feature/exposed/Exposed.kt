package dev.whyoleg.kamp.feature.exposed

import dev.whyoleg.kamp.dependency.*

object Exposed {
    val defaultVersion = "0.23.1"
    fun dependencies(version: String = defaultVersion): ExposedDependencies = ExposedDependencies(version)
    val repository = RepositoryProviders.bintray("kotlin", "exposed")
}
