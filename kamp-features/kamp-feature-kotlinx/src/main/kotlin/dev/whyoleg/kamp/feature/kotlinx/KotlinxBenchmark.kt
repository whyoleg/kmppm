package dev.whyoleg.kamp.feature.kotlinx

import dev.whyoleg.kamp.dependency.*

class KotlinxBenchmark(version: String = KotlinxVersions.Default.benchmark) : KampDependency by KotlinxGroup.benchmark(version) {
    val plugin = artifact("kotlinx.benchmark.gradle").platforms(KampPlatform.jvm)
}
