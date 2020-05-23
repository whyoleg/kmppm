package dev.whyoleg.kamp.feature.kotlinx

data class KotlinxVersions(
    val coroutines: String = "1.3.5",
    val serialization: String = "0.20.0",
    val atomicfu: String = "0.14.2",
    val cli: String = "0.2.1",
    val immutableCollections: String = "0.3.1",
    val benchmark: String = "0.2.0-dev-8"
) {
    companion object {
        val Default = KotlinxVersions()
    }
}
