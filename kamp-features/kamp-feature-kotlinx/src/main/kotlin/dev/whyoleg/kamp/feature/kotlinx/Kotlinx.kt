package dev.whyoleg.kamp.feature.kotlinx

import dev.whyoleg.kamp.dependency.*

object Kotlinx {
    val defaultVersions = KotlinxVersions.Default
    fun dependencies(versions: KotlinxVersions = defaultVersions): KotlinxDependencies = KotlinxDependencies(versions)

    val plugins = KotlinxPlugins
    val optIn = KotlinxOptIn
    val repository = RepositoryProviders.bintrayOrg("kotlin", "kotlinx")
}
