package dev.whyoleg.kamp.feature.logging

data class LoggingVersions(
    val logging: String = "1.7.9",
    val logback: String = "1.2.6",
    val slf4j: String = "1.7.26"
) {
    companion object {
        val Default = LoggingVersions()
    }
}
