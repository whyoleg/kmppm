package dev.whyoleg.kamp.feature.kamp

import dev.whyoleg.kamp.dependency.*

object KampDependencies {
    val features = KampFeatures
    val publication = KampGroup.artifact("kamp-publication")
    val dependencies = KampGroup.artifact("kamp-dependencies")
}
