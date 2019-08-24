package dev.whyoleg.kamp.builtin

import org.gradle.api.*
import kotlin.reflect.full.*

data class BuiltInVersions(
    val kamp: String = "0.1.3",
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
    val bintray: String = "1.8.4",
    val buildScan: String = "2.4.1"
)

fun Project.readVersions(): BuiltInVersions {
    val file = rootDir.resolve("buildSrc/build/versions.properties")
    println("Read versions from: ${file.absoluteFile}")
    val constructor = BuiltInVersions::class.primaryConstructor!!
    val parameters = constructor.parameters.associateBy { it.name!! }.toMap()
    val arguments = file.readLines().map { it.split("=") }.associate { (name, value) -> parameters.getValue(name) to value }
    return constructor.callBy(arguments)
}

fun Project.writeVersions(versions: BuiltInVersions) {
    buildDir.mkdirs()
    val file = buildDir.resolve("versions.properties")
    println("Write versions to: ${file.absoluteFile}")
    val text = BuiltInVersions::class.memberProperties.joinToString("\n") { "${it.name}=${it.get(versions)}" }
    file.writeText(text)
}
