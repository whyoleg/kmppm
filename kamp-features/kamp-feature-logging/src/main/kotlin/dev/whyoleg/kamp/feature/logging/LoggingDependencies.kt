package dev.whyoleg.kamp.feature.logging

import dev.whyoleg.kamp.dependency.*

class LoggingDependencies(versions: LoggingVersions = LoggingVersions.Default) {
    val logging =
        group("io.github.microutils")
            .artifact("kotlin-logging")
            .version(versions.logging)
            .platforms(KampPlatform.common("common"), KampPlatform.jvm(), KampPlatform.js("js"))

    val slf4j =
        group("org.slf4j")
            .artifact("slf4j-api")
            .version(versions.slf4j)
            .platforms(KampPlatform.jvm)

    val julToSlf4j = slf4j.artifact("jul-to-slf4j")

    val logback =
        group("ch.qos.logback")
            .artifact("logback-classic")
            .version(versions.logback)
            .platforms(KampPlatform.jvm)

}
