package dev.whyoleg.kamp.feature.kotlinx

class KotlinxDependencies(versions: KotlinxVersions = KotlinxVersions.Default) {
    val serialization = KotlinxSerialization(versions.serialization)
    val coroutines = KotlinxCoroutines(versions.coroutines)
    val atomicfu = KotlinxAtomicfu(versions.atomicfu)
    val benchmark = KotlinxBenchmark(versions.benchmark)

    val cli = KotlinxGroup.cli(versions.cli)
    val immutableCollections = KotlinxGroup.immutableCollections(versions.immutableCollections)
}
