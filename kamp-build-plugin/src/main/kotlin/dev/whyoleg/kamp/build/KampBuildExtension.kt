package dev.whyoleg.kamp.build

class KampBuildExtension internal constructor() {
    internal val features = mutableSetOf<KampFeature>()

    var publication = false

    fun features(vararg features: KampFeature) {
        this.features += features
    }

    fun features(configure: KampFeaturesBuilder.() -> Unit) {
        this.features += KampFeaturesBuilder().apply(configure).features()
    }
}

