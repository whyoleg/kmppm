package dev.whyoleg.kamp.feature.gradle

data class GradleVersions(
    val buildScan: String = "3.1.1"
) {
    companion object {
        val Default = GradleVersions()
    }
}
