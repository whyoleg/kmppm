package dev.whyoleg.kamp.feature.kotlinx

import dev.whyoleg.kamp.dependency.*

object KotlinxGroup : Group by group("org.jetbrains.kotlinx") {

    fun coroutines(version: String = KotlinxVersions.Default.coroutines): KampDependency =
        artifact("kotlinx-coroutines-core").version(version).platforms(KampPlatform.oldStyle)

    fun serialization(version: String = KotlinxVersions.Default.serialization): KampDependency =
        artifact("kotlinx-serialization-runtime").version(version).platforms(KampPlatform.oldStyle)

    fun atomicfu(version: String = KotlinxVersions.Default.atomicfu): KampDependency =
        artifact("atomicfu").version(version).platforms(KampPlatform.oldStyle)


    fun benchmark(version: String = KotlinxVersions.Default.benchmark): KampDependency =
        artifact("kotlinx.benchmark.runtime").version(version).platforms(KampPlatform.newStyle)

    fun cli(version: String = KotlinxVersions.Default.cli): KampDependency =
        artifact("kotlinx-cli").version(version).platforms(KampPlatform.newStyle)

    fun immutableCollections(version: String = KotlinxVersions.Default.immutableCollections): KampDependency =
        artifact("kotlinx-collections-immutable").version(version).platforms(KampPlatform.newStyle)

}
