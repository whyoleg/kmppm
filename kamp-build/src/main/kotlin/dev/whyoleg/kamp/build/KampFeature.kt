package dev.whyoleg.kamp.build

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.feature.kamp.*

enum class KampFeature(val dependency: KampDependency) {
    Android(KampFeatures.android),
    Exposed(KampFeatures.exposed),
    Gradle(KampFeatures.gradle),
    Jib(KampFeatures.jib),
    Kamp(KampFeatures.kamp),
    Kotlin(KampFeatures.kotlin),
    Kotlinx(KampFeatures.kotlinx),
    Ktor(KampFeatures.ktor),
    Logging(KampFeatures.logging),
    Shadow(KampFeatures.shadow),
    Updates(KampFeatures.updates)
}
