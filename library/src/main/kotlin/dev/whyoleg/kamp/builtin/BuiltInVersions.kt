package dev.whyoleg.kamp.builtin

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.gradle.api.*

@Serializable
data class BuiltInVersions(
    val kamp: String = "0.1.2",
    val kotlin: String = "1.3.50",
    val coroutines: String = "1.3.0",
    val serialization: String = "0.12.0",
    val atomicfu: String = "0.12.11",
    val ktor: String = "1.2.3",
    val updates: String = "0.22.0",
    val docker: String = "1.5.0",
    val shadow: String = "5.1.0",
    val detekt: String = "1.0.0",
    val koin: String = "2.0.1",
    val logging: String = "1.7.6",
    val logback: String = "1.2.3",
    val slf4j: String = "1.7.26",
    val versioning: String = "2.8.2",
    val bintray: String = "1.8.4"
)

private val json = Json(JsonConfiguration.Stable)

fun Project.readVersions(): BuiltInVersions =
    json.parse(BuiltInVersions.serializer(), rootDir.resolve("buildSrc/build/versions.json").readText())

fun Project.writeVersions(versions: BuiltInVersions) {
    buildDir.resolve("versions.json").writeText(json.stringify(BuiltInVersions.serializer(), versions))
}
