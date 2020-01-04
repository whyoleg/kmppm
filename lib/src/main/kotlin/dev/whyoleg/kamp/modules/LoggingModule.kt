package dev.whyoleg.kamp.modules

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.platform.KampPlatform.*

data class LoggingVersions(
    val microutilsLogging: String,
    val logback: String,
    val slf4j: String
) {
    companion object {
        val Stable = LoggingVersions(
            microutilsLogging = "1.7.8",
            logback = "1.2.6",
            slf4j = "1.7.26"
        )
        val Alpha = Stable.copy(
            logback = "1.3.0-alpha4",
            slf4j = "2.0.0-alpha1"
        )
    }
}

class LoggingDependencies(versions: LoggingVersions) {
    companion object {
        val Stable: LoggingDependencies by lazy { LoggingDependencies(LoggingVersions.Stable) }
        val Alpha: LoggingDependencies by lazy { LoggingDependencies(LoggingVersions.Alpha) }
    }

    val logging =
        group("io.github.microutils", RepositoryProviders.mavenCentral)
            .artifact("kotlin-logging")
            .version(versions.microutilsLogging)
            .platforms(common("common"), jvm())

    val slf4j =
        group("org.slf4j", RepositoryProviders.mavenCentral)
            .artifact("slf4j-api")
            .version(versions.slf4j).jvm
    val julToSlf4j = slf4j.artifact("jul-to-slf4j")
    val logback =
        group("ch.qos.logback", RepositoryProviders.mavenCentral)
            .artifact("logback-classic")
            .version(versions.logback).jvm

}
