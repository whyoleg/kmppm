package dev.whyoleg.kamp

import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.ext.*
import org.gradle.api.*
import org.gradle.api.initialization.*


@KampDSL
fun Settings.kamp(block: KampSettings.() -> Unit): Unit = KampSettings().apply(block).configure(this)

@KampDSL
fun Project.kampBuild(versions: BuiltInVersions = BuiltInVersions(), block: KampBuild.() -> Unit): Unit =
    KampBuild(true).apply(block).configure(versions, this)

@KampDSL
fun Project.kampRoot(block: KampRoot.() -> Unit): Unit = KampRoot().apply(block).configure(this)

@KampDSL
fun Project.kamp(configuration: ProjectConfiguration, block: KampMultiPlatformExtension.() -> Unit): Unit =
    KampMultiPlatformExtension(configuration).apply(block).configure(this)

@KampDSL
fun Project.kampJvm(configuration: ProjectConfiguration, block: KampJvmExtension.() -> Unit): Unit =
    KampJvmExtension(configuration).apply(block).configure(this)

@Experimental
annotation class KampInternal

@KampInternal
@KampDSL
fun Project.kampBuildDev(versions: BuiltInVersions = BuiltInVersions(), block: KampBuild.() -> Unit) {
    val files = files("../library/build/classes/kotlin/main")
    val localBuildExists = files.singleFile.exists()
    if (localBuildExists) dependencies.add("implementation", (files("../library/build/classes/kotlin/main")))
    KampBuild(!localBuildExists).apply(block).configure(versions, this)
}