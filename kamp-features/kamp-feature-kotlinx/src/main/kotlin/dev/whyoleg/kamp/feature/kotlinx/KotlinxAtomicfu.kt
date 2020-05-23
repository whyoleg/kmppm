package dev.whyoleg.kamp.feature.kotlinx

import dev.whyoleg.kamp.dependency.*

class KotlinxAtomicfu(version: String = KotlinxVersions.Default.atomicfu) : KampDependency by KotlinxGroup.atomicfu(version) {
    val plugin = artifact("atomicfu-gradle-plugin").platforms(KampPlatform.jvm)
}
