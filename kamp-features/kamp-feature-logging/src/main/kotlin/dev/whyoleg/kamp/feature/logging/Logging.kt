package dev.whyoleg.kamp.feature.logging

object Logging {
    val defaultVersions = LoggingVersions.Default
    fun dependencies(versions: LoggingVersions = defaultVersions): LoggingDependencies = LoggingDependencies(versions)
}
