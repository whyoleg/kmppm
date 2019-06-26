package dev.whyoleg.kamp.packager

import com.google.cloud.tools.jib.gradle.*
import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.plugin.Plugin
import org.gradle.api.*

@Suppress("UnstableApiUsage")
class DockerPackager : Packager {
    var baseImage: String? = null
    var image: String? = null
    var jdk: Int? = null
    var tags: Set<String> = emptySet()
    var ports: Set<String> = emptySet()
    var versionTag: Boolean = true
    var latestTag: Boolean = true
    var className: String? = null
    var jvmFlags: List<String> = emptyList()

    //                    listOf(
//                        "-server",
//                        "-Djava.awt.headless=true",
//                        "-XX:+UnlockExperimentalVMOptions",
//                        "-XX:+UseG1GC",
//                        "-XX:MaxGCPauseMillis=100",
//                        "-XX:+UseStringDeduplication",
//                        "-Djava.library.path=\"/app/libs:\${PATH}\""
//                    )

    override val plugins: Set<Plugin> = setOf(BuiltInPlugins.docker)

    override fun Project.configure() {
        val iName = path.replace(":", "-").drop(1)
        val imageName = image ?: iName
        val packageName = iName.replace("-", ".")
        val mainClassName = className ?: "$group.$packageName.AppKt"
        val dockerImage = baseImage ?: "adoptopenjdk/openjdk$jdk:alpine-slim"
        println("Setup dpcker '$imageName' with main class '$mainClassName'")
        extensions.configure<JibExtension>("jib") { jib ->
            jib.apply {
                from {
                    it.image = dockerImage
                }
                to {
                    it.image = imageName
                    val tags = mutableSetOf<String>()
                    tags += this@DockerPackager.tags
                    if (versionTag) tags += version.toString()
                    if (latestTag) tags += "latest"
                    it.tags = tags
                }
                container {
                    it.ports = ports.toList()
                    it.mainClass = mainClassName
                    it.useCurrentTimestamp = true
                    it.jvmFlags = jvmFlags
                }
            }
        }
    }
}