package dev.whyoleg.kamp.version

import org.gradle.api.*
import kotlin.reflect.full.*

data class BuiltInVersions(
    val kamp: String,
    val kotlin: String,
    val coroutines: String,
    val serialization: String,
    val atomicfu: String,
    val ktor: String,
    val updates: String,
    val docker: String,
    val shadow: String,
    val detekt: String,
    val koin: String,
    val logging: String,
    val logback: String,
    val slf4j: String,
    val bintray: String,
    val buildScan: String,
    val android: String
) {
    companion object {
        val Stable: BuiltInVersions = BuiltInVersions(
            kamp = "0.1.13",
            kotlin = "1.3.50",
            coroutines = "1.3.2",
            serialization = "0.13.0",
            atomicfu = "0.13.2",
            ktor = "1.2.3",
            updates = "0.26.0",
            docker = "1.6.1",
            shadow = "5.1.0",
            detekt = "1.1.1",
            koin = "2.0.1",
            logging = "1.7.6",
            logback = "1.2.3",
            slf4j = "1.7.26",
            bintray = "1.8.4",
            buildScan = "2.4.2",
            android = "3.5.0"
        )
        val Latest: BuiltInVersions = Stable.copy(
            kotlin = "1.3.60-eap-23",
            ktor = "1.3.0-beta-1",
            koin = "2.1.0-alpha-1",
            logback = "1.3.0-alpha4",
            slf4j = "2.0.0-alpha1",
            android = "3.5.0"
        )
    }
}

fun Project.readVersions(kind: String): BuiltInVersions {
    val file = rootDir.resolve("buildSrc/build/versions/${kind.toLowerCase()}.properties")
    if (!file.exists()) noVersionsRegistered(kind)
    println("Read versions from: ${file.absoluteFile}")
    val constructor = BuiltInVersions::class.primaryConstructor!!
    val parameters = constructor.parameters.associateBy { it.name!! }.toMap()
    val arguments = file.readLines().map { it.split("=") }.associate { (name, value) -> parameters.getValue(name) to value }
    return constructor.callBy(arguments)
}

fun Project.writeVersions(kind: String, versions: BuiltInVersions) {
    val root = buildDir.resolve("versions")
    root.mkdirs()
    val file = root.resolve("${kind.toLowerCase()}.properties")
    println("Write versions to: ${file.absoluteFile}")
    val text = BuiltInVersions::class.memberProperties.joinToString("\n") { "${it.name}=${it.get(versions)}" }
    file.writeText(text)
}