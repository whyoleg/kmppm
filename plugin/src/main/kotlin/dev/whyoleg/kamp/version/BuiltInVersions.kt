package dev.whyoleg.kamp.version

data class BuiltInVersions(
    val kamp: String,
    val kotlin: String,
    val coroutines: String,
    val serialization: String,
    val atomicfu: String,
    val ktor: String,
    val gradleVersions: String,
    val jib: String,
    val shadow: String,
    val detekt: String,
    val koin: String,
    val microutilsLogging: String,
    val logback: String,
    val slf4j: String,
    val bintray: String,
    val buildScan: String,
    val androidPlugin: String
) {
    companion object {
        val Stable: BuiltInVersions = BuiltInVersions(
            kamp = "0.1.16",
            kotlin = "1.3.50",
            coroutines = "1.3.2",
            serialization = "0.13.0",
            atomicfu = "0.13.2",
            ktor = "1.2.3",
            gradleVersions = "0.26.0",
            jib = "1.6.1",
            shadow = "5.1.0",
            detekt = "1.1.1",
            koin = "2.0.1",
            microutilsLogging = "1.7.6",
            logback = "1.2.3",
            slf4j = "1.7.26",
            bintray = "1.8.4",
            buildScan = "2.4.2",
            androidPlugin = "3.5.0"
        )
        val Latest: BuiltInVersions = Stable.copy(
            kotlin = "1.3.60-eap-23",
            ktor = "1.3.0-beta-1",
            koin = "2.1.0-alpha-1",
            logback = "1.3.0-alpha4",
            slf4j = "2.0.0-alpha1"
        )
    }
}
