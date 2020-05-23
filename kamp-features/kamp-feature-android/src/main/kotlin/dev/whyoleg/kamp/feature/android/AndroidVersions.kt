package dev.whyoleg.kamp.feature.android

data class AndroidVersions(
    val plugin: String = "3.5.3"
) {
    companion object {
        val Default = AndroidVersions()
    }
}
