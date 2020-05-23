package dev.whyoleg.kamp.build

class KampFeaturesBuilder internal constructor() {
    var android: Boolean = false
    var exposed: Boolean = false
    var gradle: Boolean = false
    var jib: Boolean = false
    var kotlin: Boolean = false
    var kotlinx: Boolean = false
    var ktor: Boolean = false
    var logging: Boolean = false
    var shadow: Boolean = false
    var updates: Boolean = false
    var kamp: Boolean = false

    @OptIn(ExperimentalStdlibApi::class)
    internal fun features() = buildSet {
        if (android) add(KampFeature.Android)
        if (exposed) add(KampFeature.Exposed)
        if (gradle) add(KampFeature.Gradle)
        if (jib) add(KampFeature.Jib)
        if (kamp) add(KampFeature.Kamp)
        if (kotlin) add(KampFeature.Kotlin)
        if (kotlinx) add(KampFeature.Kotlinx)
        if (ktor) add(KampFeature.Ktor)
        if (logging) add(KampFeature.Logging)
        if (shadow) add(KampFeature.Shadow)
        if (updates) add(KampFeature.Updates)
    }
}
